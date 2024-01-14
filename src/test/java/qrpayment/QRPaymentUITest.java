package qrpayment;

import config.Configs;
import finxCommon.BaseTest;
import finxPages.home.HomePage;
import finxPages.login.PreLoginPage;
import finxPages.register.RegisterInputPhonePage;
import login.LoginTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Log;
import utilities.Reports;

public class QRPaymentUITest extends BaseTest {
    PreLoginPage preLoginPage;
    HomePage homePage;
    LoginTest loginTest;

    @Test(priority = 1)
    public void TC_QRPayment_DisplayQuetMaQR() throws Exception{
        preLoginPage = new PreLoginPage(driver);
        homePage = new HomePage(driver);
        loginTest = new LoginTest();

        logger = extent.startTest("Testcase QR Payment Display Quét Mã QR button");
        Log.addLog(logger, "Open app, look & feel");

        preLoginPage.actionLoginWithSmartPassword();
        if (homePage.isDisplayQuetMaQR()) {
            Log.addInfoLog(logger, "Display Quét Mã QR Button");
            Thread.sleep(Configs.ServerSleep);

            String imgName = "TC_QRPayment_DisplayQuetMaQR";
            screenshot.takeScreenshot(imgName);
            boolean check = homePage.isDisplayQuetMaQR();
            Reports.reportWithIsDisplay(logger, check, "Display Quét Mã QR Button",imgName);
            Assert.assertTrue(check);
        } else {
            Reports.reportWithIsDisplay(logger, false, "Don't display Quét Mã QR Button");
            Assert.assertTrue(false);
        }
    }
}
