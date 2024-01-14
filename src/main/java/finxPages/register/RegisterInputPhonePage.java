package finxPages.register;

import finxCommon.FinXBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.PageFactory;

public class RegisterInputPhonePage extends FinXBase {

    /**
     * ==============================================================================================================
     * Constructor
     */
    public RegisterInputPhonePage(AppiumDriver driver) {
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
