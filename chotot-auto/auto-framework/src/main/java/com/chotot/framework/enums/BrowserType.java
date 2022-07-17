package com.chotot.framework.enums;

import com.chotot.framework.base.DriverSetup;
import com.chotot.framework.base.TestDevice;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import static org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum BrowserType implements DriverSetup {

  CHROME {
    public RemoteWebDriver getWebDriver(MutableCapabilities capabilities, String downloadPath, boolean isHeadless) {
      WebDriverManager.chromedriver().setup();
      if (!capabilities.asMap().isEmpty()) {
        ChromeOptions chromeOptions = (ChromeOptions) capabilities;
        return new ChromeDriver(chromeOptions);
      }
      return new ChromeDriver(getChromeCapabilities(downloadPath, isHeadless));
    }
  }, IE {
    public RemoteWebDriver getWebDriver(MutableCapabilities capabilities, String downloadPath, boolean isHeadless) {
      WebDriverManager.iedriver().setup();
      return new InternetExplorerDriver(getInternetExplorerCapabilities());
    }
  };


  public static ChromeOptions getChromeCapabilities(String downloadPath, boolean isHeadless) {
    ChromeOptions chromeOptions = new ChromeOptions();

    if (isHeadless) {
      chromeOptions.addArguments("--headless");
    }

    chromeOptions.setCapability(ACCEPT_SSL_CERTS, true);
    chromeOptions.addArguments("--no-sandbox");
    chromeOptions.addArguments("--disable-dev-shm-usage");
    chromeOptions.addArguments("--disable-notifications");

    HashMap<String, Object> chromeLocalStatePrefs = new HashMap<>();
    List<String> experimentalFlags = new ArrayList<>();
    experimentalFlags.add("calculate-native-win-occlusion@2");
    chromeLocalStatePrefs.put("browser.enabled_labs_experiments", experimentalFlags);
    chromeOptions.setExperimentalOption("localState", chromeLocalStatePrefs);

    //set download path
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("download.default_directory", downloadPath);
    chromeOptions.setExperimentalOption("prefs", prefs);

    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.BROWSER, Level.ALL);
    chromeOptions.setCapability("goog:loggingPrefs", logPrefs);
    chromeOptions.addArguments("--enable-logging --v=1");

    return chromeOptions;
  }

  public static InternetExplorerOptions getInternetExplorerCapabilities() {
    InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
    internetExplorerOptions.setCapability("EnableNativeEvents", false);
    internetExplorerOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
        true);
    internetExplorerOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
    return internetExplorerOptions;
  }

  public static ChromeOptions getChromeMobileEmulationCapabilities(TestDevice testDevice, String downloadPath, boolean isHeadless) {
    ChromeOptions chromeOptions = getChromeCapabilities(downloadPath, isHeadless);
    Map<String, Object> deviceMetrics = new HashMap<>();
    deviceMetrics.put("width", testDevice.getScreenSize().width);
    deviceMetrics.put("height", testDevice.getScreenSize().height);
    deviceMetrics.put("pixelRatio", 3.0);
    Map<String, Object> mobileEmulation = new HashMap<>();
    mobileEmulation.put("deviceMetrics", deviceMetrics);
    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

    return chromeOptions;
  }
}
