package finxPages.login;

import config.Configs;
import finxCommon.FinXBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Log;

public class PreLoginPage extends FinXBase {
    /**
     * ==============================================================================================================
     * Android
     */
    private static final String AndHeader = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.HorizontalScrollView";
    private static final String AndBody = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup";
    private static final String AndPopup = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup";

    /**
     * [Android] header
     */
    @FindBy(xpath = AndHeader + "")
    private WebElement AND_SLIDE_BAR;

    /**
     * [Android] body
     */
    @FindBy(xpath = AndBody + "/android.view.ViewGroup/android.view.ViewGroup[1]")
    private WebElement AND_LANGUAGE_BUTTON;
    private static final String AndGroupText = "//android.widget.TextView";
    @FindBy(xpath = AndBody + "/android.view.ViewGroup/android.view.ViewGroup[5]")
    private WebElement AND_EXPLORE_BUTTON;
    @FindBy(xpath = AndBody + "/android.view.ViewGroup/android.view.ViewGroup[6]")
    private WebElement AND_CREATE_ACCOUNT_BUTTON;
    @FindBy(xpath = AndBody + "/android.view.ViewGroup/android.view.ViewGroup[7]")
    private WebElement AND_LOGIN_LINK;

    /**
     * [Android] popup
     */
    @FindBy(xpath = AndPopup + "/android.widget.TextView")
    private WebElement AND_POPUP_CHOICE_LANGUAGE_TITLE;
    // List language can handle with List<WebElement>, currently it doesn't needed
    @FindBy(xpath = AndPopup + "/android.view.ViewGroup[1]")
    private WebElement AND_POPUP_VN_OPTION;
    @FindBy(xpath = AndPopup + "/android.view.ViewGroup[3]")
    private WebElement AND_POPUP_EN_OPTION;

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
    // need double-check if your device setting language is VN
    @FindBy(xpath = IOSHeader + "//XCUIElementTypeOther[@name=\"English\"]")
    private WebElement IOS_LANGUAGE_BUTTON;

    @FindBy(xpath = IOSHeader + "")
    private WebElement IOS_SLIDE_BAR;

    /**
     * [IOS] body
     */
    @FindBy(xpath = IOSBody + "//XCUIElementTypeOther[@name=\"Explore now\"]")
    private WebElement IOS_EXPLORE_BUTTON;
    @FindBy(xpath = IOSBody + "//XCUIElementTypeOther[@name=\"Create account\"]")
    private WebElement IOS_CREATE_ACCOUNT_BUTTON;
    @FindBy(xpath = IOSBody + "//XCUIElementTypeOther[@name=\"Log in to existing account\"]")
    private WebElement IOS_LOGIN_LINK;

    @FindBy(xpath = IOSBody + "//XCUIElementTypeOther[@name=\"1\"]")
    private WebElement IOS_BTN_ONE;

    @FindBy(xpath = IOSBody + "//XCUIElementTypeOther[@name=\"2\"]")
    private WebElement IOS_BTN_TWO;
    @FindBy(xpath = IOSBody + "//XCUIElementTypeOther[@name=\"3\"]")
    private WebElement IOS_BTN_THREE;

    /**
     * [IOS] popup
     */
    @FindBy(xpath = IOSFooter + "//XCUIElementTypeStaticText[@name=\"Change language\"]")
    private WebElement IOS_POPUP_CHOICE_LANGUAGE_TITLE;
    // List language can handle with List<WebElement>, currently it doesn't needed
    @FindBy(xpath = IOSFooter + "//XCUIElementTypeOther[@name=\"Tiếng Việt\"]")
    private WebElement IOS_POPUP_VN_OPTION;
    @FindBy(xpath = IOSFooter + "//XCUIElementTypeOther[@name=\"English\"])")
    private WebElement IOS_POPUP_EN_OPTION;
    @FindBy(xpath = IOSFooter + "//XCUIElementTypeOther")
    private WebElement IOS_FORGOTPIN_BUTTON;



    /**
     * ==============================================================================================================
     * Constructor
     */
    public PreLoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void actionLoginWithSmartPassword() {
        clickBtnOne();
        clickBtnOne();
        clickBtnTwo();
        clickBtnTwo();
        clickBtnThree();
        clickBtnThree();
    }

    /**
     * Object: LANGUAGE_BUTTON
     */
    public boolean isDisplayLanguageButton() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_LANGUAGE_BUTTON.isDisplayed();
            } else {
                isDisplay = IOS_LANGUAGE_BUTTON.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of LANGUAGE_BUTTON");
        }
        return isDisplay;
    }

    public boolean isDisplayForgotPIN() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_LANGUAGE_BUTTON.isDisplayed();
            } else {
                isDisplay = IOS_FORGOTPIN_BUTTON.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of FORGOTPIN_BUTTON");
        }
        return isDisplay;
    }
    public boolean isDisplayBtnOne() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_LANGUAGE_BUTTON.isDisplayed();
            } else {
                isDisplay = IOS_BTN_ONE.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of ONE_BUTTON");
        }
        return isDisplay;
    }
    public String getCurrentLanguage() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_LANGUAGE_BUTTON.findElement(By.xpath(AndGroupText)).getText();
            } else {
                text = IOS_LANGUAGE_BUTTON.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of LANGUAGE_BUTTON");
        }
        return text;
    }
    public void clickOnLanguageButton() {
        try {
            if (Configs.isAndroid) {
                AND_LANGUAGE_BUTTON.click();
            } else {
                IOS_LANGUAGE_BUTTON.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of LANGUAGE_BUTTON");
        }
    }

    /**
     * Object: EXPLORE_BUTTON
     */
    public boolean isDisplayExploreButton() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_EXPLORE_BUTTON.isDisplayed();
            } else {
                isDisplay = IOS_EXPLORE_BUTTON.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of EXPLORE_BUTTON");
        }
        return isDisplay;
    }
    public String getExploreButtonText() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_EXPLORE_BUTTON.findElement(By.xpath(AndGroupText)).getText();
            } else {
                text = IOS_EXPLORE_BUTTON.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of EXPLORE_BUTTON");
        }
        return text;
    }
    public void clickOnExploreButton() {
        try {
            if (Configs.isAndroid) {
                AND_EXPLORE_BUTTON.click();
            } else {
                IOS_EXPLORE_BUTTON.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of EXPLORE_BUTTON");
        }
    }

    /**
     * Object: CREATE_ACCOUNT_BUTTON
     */
    public boolean isDisplayCreateAccountButton() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_CREATE_ACCOUNT_BUTTON.isDisplayed();
            } else {
                isDisplay = IOS_CREATE_ACCOUNT_BUTTON.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of CREATE_ACCOUNT_BUTTON");
        }
        return isDisplay;
    }
    public String getCreateAccountButtonText() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_CREATE_ACCOUNT_BUTTON.findElement(By.xpath(AndGroupText)).getText();
            } else {
                text = IOS_CREATE_ACCOUNT_BUTTON.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of CREATE_ACCOUNT_BUTTON");
        }
        return text;
    }
    public void clickOnCreateAccountButton() {
        try {
            if (Configs.isAndroid) {
                AND_CREATE_ACCOUNT_BUTTON.click();
            } else {
                IOS_CREATE_ACCOUNT_BUTTON.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of CREATE_ACCOUNT_BUTTON");
        }
    }

    /**
     * Object: POPUP_CHOICE_LANGUAGE_TITLE
     */
    public boolean isDisplayChoiceLanguagePopup() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_POPUP_CHOICE_LANGUAGE_TITLE.isDisplayed();
            } else {
                isDisplay = IOS_POPUP_CHOICE_LANGUAGE_TITLE.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_CHOICE_LANGUAGE_TITLE");
        }
        return isDisplay;
    }
    public String getChoiceLanguagePopupTitle() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_POPUP_CHOICE_LANGUAGE_TITLE.getText();
            } else {
                text = IOS_POPUP_CHOICE_LANGUAGE_TITLE.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_CHOICE_LANGUAGE_TITLE");
        }
        return text;
    }

    /**
     * Object: POPUP_VN_OPTION
     */
    public boolean isDisplayVnOption() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_POPUP_VN_OPTION.isDisplayed();
            } else {
                isDisplay = IOS_POPUP_VN_OPTION.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_VN_OPTION");
        }
        return isDisplay;
    }
    public String getVnOptionText() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_POPUP_VN_OPTION.findElement(By.xpath(AndGroupText)).getText();
            } else {
                text = IOS_POPUP_VN_OPTION.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_VN_OPTION");
        }
        return text;
    }
    public void clickOnVnOption() {
        try {
            if (Configs.isAndroid) {
                AND_POPUP_VN_OPTION.click();
            } else {
                IOS_POPUP_VN_OPTION.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_VN_OPTION");
        }
    }

    /**
     * Object: POPUP_EN_OPTION
     */
    public boolean isDisplayEnOption() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_POPUP_EN_OPTION.isDisplayed();
            } else {
                isDisplay = IOS_POPUP_EN_OPTION.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_EN_OPTION");
        }
        return isDisplay;
    }
    public String getEnOptionText() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_POPUP_EN_OPTION.findElement(By.xpath(AndGroupText)).getText();
            } else {
                text = IOS_POPUP_EN_OPTION.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_EN_OPTION");
        }
        return text;
    }
    public void clickOnEnOption() {
        try {
            if (Configs.isAndroid) {
                AND_POPUP_EN_OPTION.click();
            } else {
                IOS_POPUP_EN_OPTION.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of POPUP_EN_OPTION");
        }
    }

    /** ------------------------------------------------------------------------------------------------ */
    /**
     * Object: LOGIN_LINK
     */
    public boolean isDisplayLoginLink() {
        boolean isDisplay = false;
        try {
            if (Configs.isAndroid) {
                isDisplay = AND_LOGIN_LINK.isDisplayed();
            } else {
                isDisplay = IOS_LOGIN_LINK.isDisplayed();
            }
        } catch (Exception e) {
            Log.addLog("xPath of LOGIN_LINK");
        }
        return isDisplay;
    }
    public String getLoginLink() {
        String text = "";
        try {
            if (Configs.isAndroid) {
                text = AND_LOGIN_LINK.findElement(By.xpath(AndGroupText)).getText();
            } else {
                text = IOS_LOGIN_LINK.getText();
            }
        } catch (Exception e) {
            Log.addLog("xPath of LOGIN_LINK");
        }
        return text;
    }
    public void clickOnLoginLink() {
        try {
            if (Configs.isAndroid) {
                AND_LOGIN_LINK.click();
            } else {
                IOS_LOGIN_LINK.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of LOGIN_LINK");
        }
    }

    public void clickBtnOne() {
        try {
            if (Configs.isAndroid) {
                AND_LOGIN_LINK.click();
            } else {
                IOS_BTN_ONE.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of BTN_ONE");
        }
    }

    public void clickBtnOne1() {
        try {
            if (Configs.isAndroid) {
                AND_LOGIN_LINK.click();
            } else {
                IOS_BTN_ONE.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of BTN_ONE");
        }
    }

    public void clickBtnTwo() {
        try {
            if (Configs.isAndroid) {
                AND_LOGIN_LINK.click();
            } else {
                IOS_BTN_TWO.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of BTN_TWO");
        }
    }

    public void clickBtnThree() {
        try {
            if (Configs.isAndroid) {
                AND_LOGIN_LINK.click();
            } else {
                IOS_BTN_THREE.click();
            }
        } catch (Exception e) {
            Log.addLog("xPath of BTN_TWO");
        }
    }


    /**
     * ==============================================================================================================
     * Common
     */
    public boolean isDisplay() {
        return isDisplayLanguageButton();
    }
}
