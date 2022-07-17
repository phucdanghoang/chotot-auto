package com.chotot.framework.utilities;

import com.chotot.framework.base.LocalDriverContext;
import com.chotot.framework.exceptions.WaitingException;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitUtil {

  private WaitUtil() {
    throw new IllegalStateException("WaitUtil class can't create an object");
  }

  private static final Logger logger = LogManager.getLogger(WaitUtil.class);
  private static final int TIME_OUT_IN_SECONDS = System.getenv("TIME_OUT_IN_SECONDS") == null
      ? 30
      : Integer.parseInt(System.getenv("TIME_OUT_IN_SECONDS"));

  public static void wait(double inSecond) {
    try {
      Thread.sleep((int) (inSecond * 1000L));
    } catch (InterruptedException e) {
      throw new WaitingException("Waiting error");
    }
  }

  public static void waitForPageLoadComplete() {
    ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver)
        .executeScript("return document.readyState")
        .equals("complete");
    RemoteWebDriver driver = LocalDriverContext.getRemoteWebDriver();
    WebDriverWait wait = new WebDriverWait(driver, TIME_OUT_IN_SECONDS);
    try {
      wait.until(pageLoadCondition);
    } catch (Exception e) {
      driver.quit();
      logger.info("Driver closed because page is hang forever");
      logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e);
    }
  }

  public static WebElement waitVisibilityOfElementLocated(WebElement element) {
    return waitVisibilityOfElementLocated(element, TIME_OUT_IN_SECONDS);
  }

  public static WebElement waitVisibilityOfElementLocated(WebElement element, int timeInSecond) {
    try {
      WebDriverWait wait = new WebDriverWait(LocalDriverContext.getRemoteWebDriver(), timeInSecond);
      ExpectedCondition<Boolean> condition = driver -> element.isDisplayed();
      wait.until(condition);
      return element;
    } catch (StaleElementReferenceException ex) {
      WebDriverWait wait = new WebDriverWait(LocalDriverContext.getRemoteWebDriver(), timeInSecond);
      ExpectedCondition<Boolean> condition = driver -> element.isDisplayed();
      wait.until(condition);
      return element;
    }
  }

  public static WebElement waitVisibilityOfElementLocated(By locator) {
    return waitVisibilityOfElementLocated(locator, TIME_OUT_IN_SECONDS);
  }

  public static WebElement waitVisibilityOfElementLocated(By locator, int timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(LocalDriverContext.getRemoteWebDriver(), timeOutInSeconds);
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public static WebElement waitElementToBeClickable(By locator, int timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(LocalDriverContext.getRemoteWebDriver(), timeOutInSeconds);
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  public static WebElement waitElementToBeClickable(By locator) {
    return waitElementToBeClickable(locator, TIME_OUT_IN_SECONDS);
  }

}
