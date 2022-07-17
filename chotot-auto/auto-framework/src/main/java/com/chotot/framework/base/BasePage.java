package com.chotot.framework.base;

import com.chotot.framework.utilities.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class BasePage {

  public static void goToURL(String url) {
    LocalDriverContext.getRemoteWebDriver().get(url);
  }

  public static void quitDriver() {
    if (LocalDriverContext.getRemoteWebDriver() != null) {
      LocalDriverContext.getRemoteWebDriver().quit();
      LocalDriverContext.removeRemoteWebDriver();
    }
  }

  public static void clearAndInputText(By locator, String value) {
    WebElement element = WaitUtil.waitVisibilityOfElementLocated(locator);
    element.clear();
    element.sendKeys(value);
  }

  public static void clickElement(By locator) {
    WebElement element = WaitUtil.waitElementToBeClickable(locator);
    element.click();
  }

  public static String getText(By locator) {
    WebElement element = WaitUtil.waitVisibilityOfElementLocated(locator);
    return element.getText();
  }

  public static boolean isElementDisplayed(WebElement element) {
    try {
      WaitUtil.waitVisibilityOfElementLocated(element);
      return element.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

}