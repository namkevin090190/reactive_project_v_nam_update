package login;

import config.Configs;
import finxCommon.BaseTest;
import finxPages.home.HomePage;
import finxPages.login.LoginInputPhonePage;
import finxPages.login.PreLoginPage;
import finxPages.register.RegisterInputPhonePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Log;
import utilities.Reports;

public class LoginTest extends BaseTest {
    PreLoginPage preLoginPage;
    HomePage homePage;
    RegisterInputPhonePage registerInputPhonePage;
    LoginInputPhonePage loginInputPhonePage;

    @Test(priority = 1)
    private void checkAllObjectDisplayOnPreLoginUI() throws Exception{
        preLoginPage = new PreLoginPage(driver);

        logger = extent.startTest("Check all objects display on Pre-Login Page");
        Log.addLog(logger, "Open app, look & feel");

        String imgName = "LoginTest_1_0";
        screenshot.takeScreenshot(imgName);
        if (!preLoginPage.isDisplay()) {
            Reports.reportWithIsDisplay(logger, true, "Display Pre-login page", imgName);

            Reports.reportWithIsDisplay(logger, preLoginPage.isDisplayLanguageButton(), "Display Language option: " + preLoginPage.getCurrentLanguage());
            Reports.reportWithIsDisplay(logger, preLoginPage.isDisplayExploreButton(), "Display Explore Now button: " + preLoginPage.getExploreButtonText());
            Reports.reportWithIsDisplay(logger, preLoginPage.isDisplayCreateAccountButton(), "Display Create Account button: " + preLoginPage.getCreateAccountButtonText());
            Reports.reportWithIsDisplay(logger, preLoginPage.isDisplayLoginLink(), "Display Login Link: " + preLoginPage.getLoginLink());
            Assert.assertTrue(true);
        } else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Pre-login page", imgName);
            Assert.assertTrue(false);
        }
    }

    @Test(priority = 2)
    private void checkInteractionOnLanguageObj() throws Exception{
        preLoginPage = new PreLoginPage(driver);

        logger = extent.startTest("Check interaction on Language obj on Pre-Login Page");
        Log.addLog(logger, "Open app, look & feel");

        if (preLoginPage.isDisplay()) {
            Log.addInfoLog(logger,"Display Pre-Login Page, click on Language");
            preLoginPage.clickOnLanguageButton();
            Thread.sleep(Configs.ServerSleep);

            String imgName = "LoginTest_2_0";
            screenshot.takeScreenshot(imgName);
            if(preLoginPage.isDisplayChoiceLanguagePopup()){
                Reports.reportWithIsDisplay(logger, true, "Display Choice Language popup", imgName);

                Reports.reportWithIsDisplay(logger, preLoginPage.isDisplayChoiceLanguagePopup(), "Popup title: " + preLoginPage.getChoiceLanguagePopupTitle());
                Reports.reportWithIsDisplay(logger, preLoginPage.isDisplayVnOption(), "Option 1: " + preLoginPage.getVnOptionText());
                Reports.reportWithIsDisplay(logger, preLoginPage.isDisplayEnOption(), "Option 2: " + preLoginPage.getEnOptionText());

                Assert.assertTrue(true);
            } else {
                Reports.reportWithIsDisplay(logger, false, "Don't display Choice Language Popup",imgName);
                Assert.assertTrue(false);
            }
        } else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Pre-login page");
            Assert.assertTrue(false);
        }
    }

    @Test(priority = 3)
    private void checkInteractionOnExploreNowButton() throws Exception{
        preLoginPage = new PreLoginPage(driver);
        homePage = new HomePage(driver);

        logger = extent.startTest("Check interaction on Explore Now button");
        Log.addLog(logger, "Open app, look & feel");

        if (!preLoginPage.isDisplay()) {
            Log.addInfoLog(logger,"Display Pre-Login Page, click on Explore Now button");
            preLoginPage.clickOnExploreButton();
            Thread.sleep(Configs.ServerSleep);

            String imgName = "LoginTest_3_0";
            screenshot.takeScreenshot(imgName);
            boolean check = homePage.isDisplay();
            Reports.reportWithIsDisplay(logger, check, "Display Home Page",imgName);
            Assert.assertTrue(check);
        } else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Pre-login page");
            Assert.assertTrue(false);
        }
    }

    @Test(priority = 4)
    private void checkInteractionOnCreateAccountButton() throws Exception{
        preLoginPage = new PreLoginPage(driver);
        registerInputPhonePage = new RegisterInputPhonePage(driver);

        logger = extent.startTest("Check interaction on Create Account button");
        Log.addLog(logger, "Open app, look & feel");

        if (preLoginPage.isDisplay()) {
            Log.addInfoLog(logger,"Display Pre-Login Page, click on Create Account button");
            preLoginPage.clickOnCreateAccountButton();
            Thread.sleep(Configs.ServerSleep);

            String imgName = "LoginTest_4_0";
            screenshot.takeScreenshot(imgName);
            boolean check = registerInputPhonePage.isDisplay();
            Reports.reportWithIsDisplay(logger, check, "Display Register - Input Phone Number Page",imgName);
            Assert.assertTrue(check);
        } else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Pre-login page");
            Assert.assertTrue(false);
        }
    }

    @Test(priority = 5)
    private void checkInteractionOnLoginLink() throws Exception{
        preLoginPage = new PreLoginPage(driver);
        loginInputPhonePage = new LoginInputPhonePage(driver);

        logger = extent.startTest("Check interaction on Login Link");
        Log.addLog(logger, "Open app, look & feel");

        if (preLoginPage.isDisplay()) {
            Log.addInfoLog(logger,"Display Pre-Login Page, click on Login Link");
            preLoginPage.clickOnLoginLink();
            Thread.sleep(Configs.ServerSleep);

            String imgName = "LoginTest_5_0";
            screenshot.takeScreenshot(imgName);
            boolean check = loginInputPhonePage.isDisplay();
            Reports.reportWithIsDisplay(logger, check, "Display Login - Input Phone Number Page",imgName);
            Assert.assertTrue(check);
        } else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Pre-login page");
            Assert.assertTrue(false);
        }
    }

    @Test(priority = 6)
    public void checkLoginSuccess() throws Exception{
        preLoginPage = new PreLoginPage(driver);
        loginInputPhonePage = new LoginInputPhonePage(driver);

        logger = extent.startTest("Check Login Success");
        Log.addLog(logger, "Open app, input correct password & login success");

        if (preLoginPage.isDisplayBtnOne()) {
            Log.addInfoLog(logger,"Display One button, start input correct password");
            preLoginPage.actionLoginWithSmartPassword();
            Thread.sleep(Configs.ServerSleep);

            String imgName = "checkLoginSuccess_6_0";
            screenshot.takeScreenshot(imgName);
            boolean check = loginInputPhonePage.isDisplay();
            Reports.reportWithIsDisplay(logger, check, "Display Login - Input Phone Number Page",imgName);
            Assert.assertTrue(check);
        }
        else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Pre-login page");
            Assert.assertTrue(false);
        }
    }

    @Test(priority = 7)
    public void checkLoginSuccess2() throws Exception{
        boolean a = true;
        preLoginPage = new PreLoginPage(driver);
        loginInputPhonePage = new LoginInputPhonePage(driver);

        logger = extent.startTest("Check Login Success");
        Log.addLog(logger, "Open app, input correct password & login success");

        if (preLoginPage.isDisplayBtnOne()) {
            Log.addInfoLog(logger,"Display One button, start input correct password");
            preLoginPage.actionLoginWithSmartPassword();
            Thread.sleep(Configs.ServerSleep);

            String imgName = "checkLoginSuccess_7_0";
            screenshot.takeScreenshot(imgName);
            boolean check = loginInputPhonePage.isDisplay();
            Reports.reportWithIsDisplay(logger, check, "Display Login - Input Phone Number Page",imgName);
            Assert.assertTrue(check);
        }
        else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Pre-login page");
            Assert.assertTrue(false);
        }
    }


}
