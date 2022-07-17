package com.chotot.test.pages;

import com.chotot.framework.base.BasePage;
import static com.chotot.framework.utilities.WaitUtil.waitForPageLoadComplete;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

  public static final By LOGIN_LINK = By.xpath("//span/b[text() = 'Đăng nhập']/../..");
  public static final By USER_NAME_TEXT = By.xpath("//a[@class='n1r59q65']//span");

  public void clickLoginButton() {
    clickElement(LOGIN_LINK);
    waitForPageLoadComplete();
  }

  public String getProfileName() {
    return getText(USER_NAME_TEXT);
  }

}
