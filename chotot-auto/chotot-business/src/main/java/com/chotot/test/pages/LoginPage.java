package com.chotot.test.pages;

import com.chotot.framework.base.BasePage;
import com.chotot.framework.utilities.WaitUtil;
import static com.chotot.framework.utilities.WaitUtil.waitForPageLoadComplete;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Log4j
public class LoginPage extends BasePage {

  public static final By PHONE_INPUT = By.xpath("//input[@class = 'i1pbvj0j' and @placeholder='Nhập SĐT của bạn']");
  public static final By PASSWORD_INPUT = By.xpath(
      "//input[@class = 'i1pbvj0j' and @placeholder='Nhập mật khẩu của bạn']");
  public static final By LOGIN_BTN = By.xpath("//button[text() = 'Đăng nhập']");
  public static final By ERROR_MESSAGE_ABOVE = By.cssSelector(".e66t3pu.error");
  public static final By ERROR_MESSAGE_BELOW = By.xpath("//p[@class = 'prswihc' and @color = '#D0021B']");


  public void login(String phone, String password) {
    inputPhoneNumber(phone);
    inputPassword(password);
    clickLogin();
    waitForPageLoadComplete();
  }

  public void inputPhoneNumber(String phoneNumber) {
    log.info("input username: " + phoneNumber);
    clearAndInputText(PHONE_INPUT, phoneNumber);
  }

  public void inputPassword(String password) {
    log.info("input password: " + password);
    clearAndInputText(PASSWORD_INPUT, password);
  }

  public void clickLogin() {
    log.info("click login button");
    clickElement(LOGIN_BTN);
  }

  public String getErrorMessageAboveText() {
    return getText(ERROR_MESSAGE_ABOVE);
  }

  public boolean isCheckErrorMessageAboveDisplays() {
    WebElement errorMessage = WaitUtil.waitVisibilityOfElementLocated(ERROR_MESSAGE_ABOVE);
    return isElementDisplayed(errorMessage);
  }

  public String getErrorMessageBelowText() {
    return getText(ERROR_MESSAGE_BELOW);
  }

  public boolean isCheckErrorMessageBelowDisplays() {
    WebElement errorMessage = WaitUtil.waitVisibilityOfElementLocated(ERROR_MESSAGE_BELOW);
    return isElementDisplayed(errorMessage);
  }
}
