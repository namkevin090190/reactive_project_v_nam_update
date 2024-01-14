package finxCommon;

import config.Configs;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Log;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class TemplatePage extends FinXBase {
    /**
     * ==============================================================================================================
     * Android
     */
    private static final String AndHeader = "";
    private static final String AndBody = "";
    private static final String AndFooter = "";

    /**
     * [Android] header
     */
    @FindBy(xpath = AndHeader + "")
    private WebElement AND_HEADER_ELEMENT_NAME;

    /**
     * [Android] body
     */
    @FindBy(xpath = AndBody + "")
    private WebElement AND_BODY_ELEMENT_NAME;

    /**
     * [Android] footer
     */
    @FindBy(xpath = AndFooter + "")
    private WebElement AND_FOOTER_ELEMENT_NAME;

    /**
     * ==============================================================================================================
     * iOS
     */
    private static final String IOSHeader = "";
    private static final String IOSBody = "";
    private static final String IOSFooter = "";

    /**
     * [IOS] header
     */
    @FindBy(xpath = IOSHeader + "")
    private WebElement IOS_HEADER_ELEMENT_NAME;

    /**
     * [IOS] body
     */
    @FindBy(xpath = IOSBody + "")
    private WebElement IOS_BODY_ELEMENT_NAME;

    /**
     * [IOS] footer
     */
    @FindBy(xpath = IOSFooter + "")
    private WebElement IOS_FOOTER_ELEMENT_NAME;

    /**
     * ==============================================================================================================
     * Constructor
     */
    public TemplatePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Object: OBJECT/ELEMENT NAME
     */
    public boolean isDisplayElementName() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_HEADER_ELEMENT_NAME.isDisplayed();
            } else {
                isDisplay = IOS_HEADER_ELEMENT_NAME.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of ELEMENT_NAME");
        }
        return isDisplay;
    }

    public String getElementName() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_HEADER_ELEMENT_NAME.getText();
            } else {
                text = IOS_HEADER_ELEMENT_NAME.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of ELEMENT_NAME");
        }
        return text;
    }

    public void clearElementName() {
        try {
            if (Configs.isAndroid) {
                AND_HEADER_ELEMENT_NAME.clear();
            } else {
                IOS_HEADER_ELEMENT_NAME.clear();
            }
        } catch (Exception e) {
            Log.addLog("xPath of ELEMENT_NAME");
        }
    }

    public void clickOnElementName() {
        try {
            if (Configs.isAndroid) {
                AND_HEADER_ELEMENT_NAME.click();
            } else {
                IOS_HEADER_ELEMENT_NAME.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of ELEMENT_NAME");
        }
    }

    public void setElementName(String text) {
        try {
            if (Configs.isAndroid) {
                AND_HEADER_ELEMENT_NAME.sendKeys(text);
            } else {
                IOS_HEADER_ELEMENT_NAME.sendKeys(text);
            }
        } catch (Exception e) {
            Log.addLog("xPath of ELEMENT_NAME");
        }
    }

    /**
     * ==============================================================================================================
     * Common
     */
    public boolean isDisplay() {
        return isDisplayElementName();
    }
}
