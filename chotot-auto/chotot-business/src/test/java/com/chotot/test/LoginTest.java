package com.chotot.test;

import com.chotot.test.constant.ErrorMessage;
import static com.chotot.test.constant.ErrorMessage.Err001;
import static com.chotot.test.constant.ErrorMessage.Err002;
import com.chotot.test.hook.TestSetup;
import com.chotot.test.model.DataConfig.User;
import com.chotot.test.pages.HomePage;
import com.chotot.test.pages.LoginPage;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends TestSetup {

  private LoginPage loginPage;
  private HomePage homePage;
  private List<User> users;

  @BeforeClass(alwaysRun = true)
  public void beforeClass() {
    homePage = new HomePage();
    loginPage = new LoginPage();
    users = data.getData().getUsers();
  }

  @Test
  public void testLogin_withValidUser_userLoginSuccess() {
    homePage.clickLoginButton();
    loginPage.login(users.get(0).getPhone(), users.get(0).getPassword());

    String actualProfileName = homePage.getProfileName();
    assertEquals(actualProfileName, users.get(0).getProfileName(), "Verify login success is failed!");
  }

  @Test
  public void testLogin_withValidUser_invalidUserProfile() {
    homePage.clickLoginButton();
    loginPage.login(users.get(1).getPhone(), users.get(1).getPassword());

    String actualProfileName = homePage.getProfileName();
    assertEquals(actualProfileName, users.get(1).getProfileName(), "Verify login success is failed!");
  }

  @Test
  public void testLogin_withInValidUser_invalidPhoneNumber_verifyErrorMessage_isDisplaySuccess() {
    homePage.clickLoginButton();
    loginPage.inputPhoneNumber(users.get(2).getPhone());

    boolean isErrorMessageDisplayed = loginPage.isCheckErrorMessageBelowDisplays();
    String actualMessage = loginPage.getErrorMessageBelowText();

    assertTrue(isErrorMessageDisplayed, "Verify error message isn't displayed!");
    assertEquals(actualMessage, getMessage(Err002), "Verify error message is failed!");
  }

  @Test
  public void testLogin_withInValidUser_invalidShortenPassword_verifyErrorMessage_isDisplaySuccess() {
    homePage.clickLoginButton();
    loginPage.login(users.get(3).getPhone(), users.get(3).getPassword());

    boolean isErrorMessageDisplayed = loginPage.isCheckErrorMessageAboveDisplays();
    String actualMessage = loginPage.getErrorMessageAboveText();

    assertTrue(isErrorMessageDisplayed, "Verify error message isn't displayed!");
    assertEquals(actualMessage, getMessage(Err001), "Verify error message is failed!");
  }

  private String getMessage(ErrorMessage message) {
    return ErrorMessage.valueOf(message.name()).getMessage();
  }

}
