package finxPages.login;

import finxCommon.FinXBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginInputPhonePage extends FinXBase {

    /**
     * ==============================================================================================================
     * Constructor
     */
    public LoginInputPhonePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    /**
     * ==============================================================================================================
     * Common
     */
    public boolean isDisplay() {
        return true;
    }
}
