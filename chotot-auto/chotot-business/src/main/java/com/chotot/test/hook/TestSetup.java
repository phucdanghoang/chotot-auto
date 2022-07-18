package com.chotot.test.hook;

import static com.chotot.framework.base.BasePage.goToURL;
import static com.chotot.framework.base.BasePage.quitDriver;
import com.chotot.framework.base.Browser;
import com.chotot.framework.base.DriverListener;
import static com.chotot.framework.base.LocalDriverContext.getRemoteWebDriver;
import com.chotot.framework.base.RemoteWebDriverConfig;
import com.chotot.framework.enums.BrowserType;
import com.chotot.framework.enums.DriverMode;
import static com.chotot.framework.enums.DriverMode.valueOf;
import static com.chotot.framework.utilities.DirectoryUtil.deleteOldReportFiles;
import static com.chotot.framework.utilities.ExtentReport.generateReport;
import static com.chotot.framework.utilities.ExtentReport.logParentTest;
import static com.chotot.framework.utilities.ExtentReport.logTestFailed;
import static com.chotot.framework.utilities.ExtentReport.logTestPassed;
import static com.chotot.framework.utilities.ExtentReport.logTestSkipped;
import static com.chotot.framework.utilities.ExtentReport.logTestStart;
import static com.chotot.framework.utilities.ExtentReport.setupReport;
import static com.chotot.framework.utilities.ScreenShot.takeScreenShot;
import com.chotot.framework.utilities.WaitUtil;
import static com.chotot.framework.utilities.WaitUtil.waitForPageLoadComplete;
import static com.chotot.test.common.ConfigReader.getYamlConfig;
import static com.chotot.test.common.ConfigReader.setYamlConfig;
import static com.chotot.test.common.DataReader.getData;
import static com.chotot.test.common.DataReader.setData;
import com.chotot.test.model.DataConfig;
import com.chotot.test.model.EnvConfig;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import static org.testng.ITestResult.CREATED;
import static org.testng.ITestResult.FAILURE;
import static org.testng.ITestResult.SKIP;
import static org.testng.ITestResult.SUCCESS;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

@Log4j
public abstract class TestSetup {
  private static final LocalDateTime START_TIME = LocalDateTime.now();
  Browser browser = new Browser();
  private final String env = System.getProperty("TEST_ENV") == null ? "local" : System.getProperty("TEST_ENV");
  private final String userDataFileName = "user";
  protected EnvConfig config;
  protected DataConfig data;

  @BeforeSuite(alwaysRun = true)
  public void setUpSuite(ITestContext testContext) {
    setYamlConfig(env);
    setData(userDataFileName);
    config = getYamlConfig();
    data = getData();
    browser.setDownloadPath(config.getDownloadDir());
    browser.setSeleniumGrid(config.getSeleniumGridHubUrl());
    setupReport(config.getReportDir());
    deleteOldReportFiles(config.getReportDir());
  }

  @BeforeMethod
  public void initWebDriver(Method method) {
    logParentTest(getClass().getName());
    logTestStart(method.getName());
    log.info("Parallel Before . Thread id is: " + Thread.currentThread().getId());

    DriverMode driverMode = valueOf(config.getDriverMode().toUpperCase(Locale.ROOT));
    BrowserType browserType = BrowserType.valueOf(config.getBrowserType().toUpperCase());

    //init webDriver
    browser.initializeBrowser(getRemoteWebDriverConfig(driverMode,
        browserType,
        config.isHeadlessMode()));

    goToURL(config.getBaseUrl());

    waitForPageLoadComplete();

  }

  @SneakyThrows
  @AfterMethod(alwaysRun = true)
  public void getResult(ITestResult iTestResult, Method method) {
    String screenshotsDir = System.getProperty("user.dir") + config.getScreenshotDir();
    screenshotsDir += START_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm")) + "/";

    String testcaseName = method.getName();
    int result = iTestResult.getStatus();

    switch (result) {
      case CREATED:
        logTestFailed("Precondition is FAILED at beforeClass");
        log.info(testcaseName + " - Precondition is FAILED at beforeClass");
        break;

      case SUCCESS:
        log.info(testcaseName + " - PASSED");
        logTestPassed("PASSED:" + testcaseName);
        break;

      case FAILURE:
        // Take a screenshot...
        String screenshotName = testcaseName.replaceAll(" ", "_");
        takeScreenShot(screenshotName, getRemoteWebDriver(), screenshotsDir);

        log.info("current URL at failed case" + getRemoteWebDriver().getCurrentUrl());
        getRemoteWebDriver().manage().logs().get(LogType.BROWSER).forEach(e -> log.info("Browser Log" + e.toString()));
        logTestFailed(iTestResult.getThrowable().toString());
        log.error(testcaseName + " - FAILED: ", iTestResult.getThrowable());
        break;

      case SKIP:
        logTestSkipped("SKIPPED: " + testcaseName);
        log.info(testcaseName + " - SKIPPED");
        break;
    }
  }

  @AfterMethod()
  public void tearDown() {
    getRemoteWebDriver().manage().deleteAllCookies();
    quitDriver();
    log.info("Remove webDriver instance from ThreadLocal...");
    WaitUtil.wait(0.5);
  }

  @AfterSuite(alwaysRun = true)
  public void tearDownSuite() {
    generateReport();
  }

  private RemoteWebDriverConfig getRemoteWebDriverConfig(DriverMode driverMode, BrowserType browserType,
      boolean isHeadless) {
    return RemoteWebDriverConfig
        .builder()
        .browserType(browserType)
        .driverMode(driverMode)
        .isHeadless(isHeadless)
        .build();
  }

}
