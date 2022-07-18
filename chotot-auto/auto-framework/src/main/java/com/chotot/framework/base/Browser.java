package com.chotot.framework.base;

import com.chotot.framework.enums.BrowserType;
import com.chotot.framework.enums.DriverMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public class Browser {

  private static final Logger logger = LogManager.getLogger(Browser.class);
  private String downloadPath = System.getProperty("DOWNLOAD_DIR");
  private String seleniumGrid = System.getProperty("SELENIUM_GRID_HUB_URL");

  public Browser() {
  }

  public void setDownloadPath(String downloadPath) {
    this.downloadPath = downloadPath;
    logger.info("Download path: " + downloadPath);
  }

  public void setSeleniumGrid(String seleniumGrid) {
    this.seleniumGrid = seleniumGrid;
  }

  public void initializeBrowser(RemoteWebDriverConfig remoteWebDriverConfig) {
    logger.info("Requesting a new driver instance...");

    RemoteWebDriver driver = getRemoteWebDriver(remoteWebDriverConfig);

    if (driver != null) {
      driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
      EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
      EventFiringWebDriver firingWebDriver = eventDriver.register(new DriverListener());
      LocalDriverContext.setRemoteWebDriver(firingWebDriver);
      maximize();
    } else {
      logger.info("No WebDriver instance was created for this test method.");
    }
  }

  private RemoteWebDriver getRemoteWebDriver(RemoteWebDriverConfig remoteWebDriverConfig) {
    RemoteWebDriver driver = null;
    DriverMode driverModeInit = remoteWebDriverConfig.getDriverMode();
    BrowserType browserType = remoteWebDriverConfig.getBrowserType();
    TestDevice testDevice = remoteWebDriverConfig.getTestDevice();
    boolean isHeadless = remoteWebDriverConfig.isHeadless();

    logger.info("Remote webdriver configuration: " + remoteWebDriverConfig.toString());

    // init remote
    if (driverModeInit == DriverMode.REMOTE) {
      if (isChromeBrowser(browserType)) {
        if (testDevice != null) {
          driver = buildDistributedChromeMobileEmulationDriver(testDevice, isHeadless);
        } else {
          driver = buildDistributedChromeDriver(isHeadless);
        }
      }
      if (isIEBrowser(browserType)) {
        driver = buildDistributedIEDriver();
      }
    }

    // init local driver
    if (driverModeInit == DriverMode.LOCAL) {
      if (testDevice != null && isChromeBrowser(browserType)) {
        ChromeOptions deviceOption = BrowserType.getChromeMobileEmulationCapabilities(testDevice,
            downloadPath,
            isHeadless);
        driver = browserType.getWebDriver(deviceOption, downloadPath, isHeadless);
      }
      if (testDevice == null) {
        MutableCapabilities capabilities = new DesiredCapabilities();
        driver = browserType.getWebDriver(capabilities, downloadPath, isHeadless);
      }
    }
    return driver;
  }

  private RemoteWebDriver buildDistributedChromeDriver(boolean isHeadless) {
    ChromeOptions options = BrowserType.getChromeCapabilities(downloadPath, isHeadless);
    return new RemoteWebDriver(getGridUrl(), options);
  }

  private RemoteWebDriver buildDistributedChromeMobileEmulationDriver(TestDevice testDevice, boolean isHeadless) {
    ChromeOptions options = BrowserType.getChromeMobileEmulationCapabilities(testDevice, downloadPath, isHeadless);
    return new RemoteWebDriver(getGridUrl(), options);
  }

  private RemoteWebDriver buildDistributedIEDriver() {
    InternetExplorerOptions options = BrowserType.getInternetExplorerCapabilities();
    return new RemoteWebDriver(getGridUrl(), options);
  }

  private URL getGridUrl() {
    URL remoteAddressUrl = null;
    String seleniumGridEndpoint = seleniumGrid;
    logger.info("RemoteWebDriver and selenium grid endpoint: " + seleniumGridEndpoint);
    try {
      remoteAddressUrl = new URL(seleniumGridEndpoint);
    } catch (MalformedURLException exception) {
      logger.error("Specified grid endpoint was not valid: " + seleniumGridEndpoint);
    }

    return remoteAddressUrl;
  }

  private boolean isChromeBrowser(BrowserType browserType) {
    return browserType.name().equalsIgnoreCase("Chrome");
  }

  private boolean isIEBrowser(BrowserType browserType) {
    return browserType.name().equalsIgnoreCase("IE");
  }

  public static void maximize() {
    LocalDriverContext.getRemoteWebDriver().manage().window().maximize();
  }
}


