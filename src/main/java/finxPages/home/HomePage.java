package finxPages.home;

import config.Configs;
import finxCommon.FinXBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utilities.Log;

public class HomePage extends FinXBase {

    /*
     * ==============================================================================================================
     * Element
     * ==============================================================================================================
     * IOS - Pre Element
     */

    private static final String IosHomePageBody = "";

    /*
     * ==============================================================================================================
     * Element
     * ==============================================================================================================
     * ANDROID - Pre Element
     */

    private static final String AndHomePageBody = "";

    /*
     * ==============================================================================================================
     * IOS - Element
     */

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Chuyển tiền Quét mã QR Nạp tiền Thanh toán hóa đơn\"]")
    private WebElement IOS_HOMEPAGE_QUET_MA_QR_VN1;

    @FindBy(xpath = IosHomePageBody + "(//XCUIElementTypeOther[@name=\"Quét mã QR\"])[3]")
    private WebElement IOS_HOMEPAGE_QUET_MA_QR_VN;


    /*
     * ==============================================================================================================
     * ANDROID - Element
     */

    @FindBy(xpath = AndHomePageBody + "(//XCUIElementTypeOther[@name=\"Quét mã QR\"])[3]")
    private WebElement AND_HOMEPAGE_QUET_MA_QR_VN;

    /**
     * ==============================================================================================================
     * Constructor
     */
    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* ==============================================================================================================
     * Quét Mã QR - Button - IsDisplay
     */
    public boolean isDisplayQuetMaQR() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_HOMEPAGE_QUET_MA_QR_VN.isDisplayed();
            } else {
                isDisplay = IOS_HOMEPAGE_QUET_MA_QR_VN.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of QUET_MA_QR_BUTTON");
        }
        return isDisplay;
    }
    /**
     * ==============================================================================================================
     * Common
     */
    public boolean isDisplay() {
        return true;
    }
}
