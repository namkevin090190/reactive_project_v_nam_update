package finxCommon;

import actions.Direction;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import config.Apps;
//import config.Configs;
import config.Configs;
import config.Devices;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
//import privates.Setting;
import privates.Setting;
import utilities.*;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class FinXBase {
    public static AppiumDriverLocalService service;
    public static AppiumDriver driver;
    public TouchAction actions;
    private static Devices device;
    private static Apps apps;
    public static Screenshot screenshot;
    public static ExtentReports extent;
    public static ExtentTest logger;
    private static boolean isPass = true;
    /**
     * report files
     */
    private static String reportUrl = "";
    private static String uploadPath = "";
    private static String uploadFileName = "";
    /**
     * log files
     */
    private static String logUrl = "";
    private static String logFileName = "";
    private static String logFilePath = "";
    /**
     * Android popup - will apply when system use only 1 format for POPUP
     */
    public static final String AndPopupTitle = "";
    public static final String AndPopupContent = "";
    public static final String AndPopupButton = "";
    /**
     * iOS popup
     */
    public static final String iOSPopupTitle = "";
    public static final String iOSPopupContent = "";
    public static final String iOSPopupButton = "";

    /**
     * These functions use to scroll/swipe
     * Created by: Chaule
     * Refactor at: 28Dec2021
     */
    public void scrollDown() {
        final int offset = 100;
        final double startPercent = 0.2;
        final double endPercent = 0.8;
        int startX, startY, endX, endY;

        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight() - offset;
        int width = dim.getWidth() - offset;

        startX = width / 2;
        endX = startX;
        startY = (int) (height * endPercent);
        endY = (int) (height * startPercent);

        actions = new TouchAction(driver);
        actions.longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, endY)).release().perform();
    }

    public void scrollDown(WebElement element) {
        final int offset = 100;
        final double startPercent = 0.2;
        final double endPercent = 0.8;
        int startX, startY, endX, endY;

        int count = 0;

        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight() - offset;
        int width = dim.getWidth() - offset;

        startX = width / 2;
        endX = startX;
        startY = (int) (height * endPercent);
        endY = (int) (height * startPercent);

        while (true) {
            if (element.isDisplayed()) {
                break;
            } else if (count >= 5) {
                Log.addLog("Can't find element");
                break;
            } else {
                actions = new TouchAction(driver);
                actions.longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, endY)).release().perform();
                count++;
            }
        }
    }

    public void scrollInElements(WebElement element, Direction direction) {
        Point point = null;
        try {
            point = element.getLocation(); // get current location of element on UI
        } catch (Exception e) {
            Log.addLog("error: " + e.getMessage());
        }

        final double startPercent = 0.20;
        final double endPercent = 0.80;
        int startX, startY, endX, endY;

        int height = 0;
        int width = 0;
        try {
            height = element.getSize().height;
            width = element.getSize().width;
        } catch (Exception e) {
            Log.addLog("Missing element height & width");
        }

        if (point != null || !(height == 0 && width == 0)) {
            switch (direction) {
                case RIGHT:
                    startX = point.x + (int) (width * endPercent);
                    startY = point.y + (int) (height * startPercent);
                    endX = point.x + (int) (width * startPercent);
                    endY = startY;
                    break;
                case LEFT:
                    startX = point.x + (int) (width * startPercent);
                    startY = point.y + (int) (height * startPercent);
                    endX = point.x + (int) (width * endPercent);
                    endY = startY;
                    break;
                case BOTTOM:
                    startX = point.x + (int) (width * startPercent);
                    startY = point.y + (int) (height * endPercent);
                    endX = startX;
                    endY = point.y + (int) (height * startPercent);
                    break;
                case TOP:
                    startX = point.x + (int) (width * startPercent);
                    startY = point.y + (int) (height * startPercent);
                    endX = startX;
                    endY = point.y + (int) (height * endPercent);
                    break;
                default:
                    Log.addLog("Don't support this case");
                    startX = 0;
                    startY = 0;
                    endX = startX;
                    endY = startY;
                    break;
            }
            Log.addLog("Debug scroll here -----------------------------");
            Log.addLog("startX: " + startX + ", startY: " + startY);
            Log.addLog("endX: " + endX + ", endY: " + endY);
            Log.addLog("------------------------------------------------");

            Point startPoint = new Point(startX, startY);
            PointOption startPointOption = new PointOption().withCoordinates(startPoint);

            Point endPoint = new Point(endX, endY);
            PointOption endPointOption = new PointOption().withCoordinates(endPoint);

            WaitOptions waitOptions = new WaitOptions().withDuration(Duration.ofMillis(Configs.ServerSleep));

            try {
                actions = new TouchAction(driver);
                actions.longPress(startPointOption).moveTo(endPointOption).release().perform();
            } catch (Exception e) {
                Log.addLog("Can't action scroll down");
            }
        } else {
            Log.addLog("Can't scroll, please check");
        }
    }

    public void scrollInElements(WebElement element, Direction direction, double startPercent, double endPercent) {
        Point point = element.getLocation(); // get current location of element on UI

        int startX, startY, endX, endY;

        int height = element.getSize().height;
        int width = element.getSize().width;

        switch (direction) {
            case RIGHT:
                startX = point.x + (int) (width * endPercent);
                startY = point.y + (int) (height * 0.2);
                endX = point.x + (int) (width * startPercent);
                endY = startY;
                break;
            case LEFT:
                startX = point.x + (int) (width * startPercent);
                startY = point.y + (int) (height * 0.2);
                endX = point.x + (int) (width * endPercent);
                endY = startY;
                break;
            case BOTTOM:
                startX = point.x + (int) (width * 0.2);
                startY = point.y + (int) (height * endPercent);
                endX = startX;
                endY = point.y + (int) (height * startPercent);
                break;
            case TOP:
                startX = point.x + (int) (width * 0.2);
                startY = point.y + (int) (height * startPercent);
                endX = startX;
                endY = point.y + (int) (height * endPercent);
                break;
            default:
                Log.addLog("Don't support this case");
                startX = 0;
                startY = 0;
                endX = startX;
                endY = startY;
                break;
        }
        Log.addLog("|| Debug scroll here -----------------------------");
        Log.addLog("|| startX: " + startX + ", startY: " + startY);
        Log.addLog("|| endX: " + endX + ", endY: " + endY);
        Log.addLog("|| ------------------------------------------------");

        Point startPoint = new Point(startX, startY);
        PointOption startPointOption = new PointOption().withCoordinates(startPoint);

        Point endPoint = new Point(endX, endY);
        PointOption endPointOption = new PointOption().withCoordinates(endPoint);

        WaitOptions waitOptions = new WaitOptions().withDuration(Duration.ofMillis(Configs.ServerSleep));

        actions = new TouchAction(driver);
        actions.longPress(startPointOption).moveTo(endPointOption).release().perform();
    }

    /**
     * These functions use to touch outside (to hide keyboard)
     * Created by: Chaule
     */
    public void touchCenter() {
        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight();
        int width = dim.getWidth();

        Point startPoint = new Point(width / 2, (int) (height * 0.4));
        PointOption startPointOption = new PointOption().withCoordinates(startPoint);

        TouchAction action = new TouchAction(driver);
        action.tap(startPointOption).release().perform();
    }

    public void touchTopRight() {
        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight();
        int width = dim.getWidth();

        Point startPoint = new Point((int) (width * 0.9), (int) (height * 0.1));
        PointOption startPointOption = new PointOption().withCoordinates(startPoint);

        TouchAction action = new TouchAction(driver);
        action.tap(startPointOption).release().perform();
    }

    public void androidBackKey() {
        driver.navigate().back();
    }

    /**
     * These functions use to set Date Picker objects
     * Created by: Chaule
     */
    public void setAndroidDateTimePicker(WebElement elementTimePicker, String dd, String mm, String yyyy) {
        List<WebElement> elements = getDatePickerElements(elementTimePicker);

        if (elements != null) {
            if (!getTimePickerCurrent(elementTimePicker, 1).equals(dd)) {
                dd = Generator.convertStringToInt(dd).toString();
                WebElement day = elements.get(1).findElement(By.xpath("//android.widget.EditText"));
                day.click();
                driver.getKeyboard().sendKeys(dd);
                day.click();
                // lỗi device
                androidBackKey();
            } else {
                Log.addLog("Day is correct");
            }

            int month = Generator.convertStringToInt(mm);
            switch (month) {
                case 1:
                    mm = "Jan";
                    break;
                case 2:
                    mm = "Feb";
                    break;
                case 3:
                    mm = "Mar";
                    break;
                case 4:
                    mm = "Apr";
                    break;
                case 5:
                    mm = "May";
                    break;
                case 6:
                    mm = "Jun";
                    break;
                case 7:
                    mm = "Jul";
                    break;
                case 8:
                    mm = "Aug";
                    break;
                case 9:
                    mm = "Sep";
                    break;
                case 10:
                    mm = "Oct";
                    break;
                case 11:
                    mm = "Nov";
                    break;
                case 12:
                    mm = "Dec";
                    break;
                default:
                    Log.addLog("Error your month, please check");
                    break;
            }
            if (!getTimePickerCurrent(elementTimePicker, 0).equals(mm)) {
                WebElement monthE = elements.get(0).findElement(By.xpath("//android.widget.EditText"));
                monthE.click();
                monthE.clear();
                driver.getKeyboard().sendKeys(mm);
                monthE.click();
                // lỗi device
                androidBackKey();
            } else {
                Log.addLog("Month is correct");
            }

            if (!getTimePickerCurrent(elementTimePicker, 2).equals(yyyy)) {
                WebElement year = elements.get(2).findElement(By.xpath("//android.widget.EditText"));
                year.click();
                driver.getKeyboard().sendKeys(yyyy);
                year.click();
                // lỗi device
                androidBackKey();
                // retry 1 time to set year (error with timepicker had 2 objects)
                year.click();
                androidBackKey();
            } else {
                Log.addLog("Year is correct");
            }

            clickOnDone(elementTimePicker);
        } else {
            Log.addLog("Missing DatePicker");
        }
    }

    public void setIOSDateTimePicker(WebElement elementTimePicker, String dd, String mm, String yyyy) {
        List<WebElement> elements = getDatePickerElements(elementTimePicker);

        if (elements != null) {
            dd = Generator.convertStringToInt(dd).toString();
            if (!getTimePickerCurrent(elementTimePicker, 0).equals(dd)) {
                elements.get(0).sendKeys(dd);
            }

            mm = "tháng " + Generator.convertStringToInt(mm).toString();
            if (!getTimePickerCurrent(elementTimePicker, 1).equals(dd)) {
                elements.get(1).sendKeys(mm);
            }

            if (!getTimePickerCurrent(elementTimePicker, 2).equals(yyyy)) {
                elements.get(2).sendKeys(yyyy);
            }

            clickOnDone(elementTimePicker);
        } else {
            Log.addLog("Missing DatePicker");
        }
    }

    private List<WebElement> getDatePickerElements(WebElement elementTimePicker) {
        List<WebElement> elements = null;
        try {
            if (Configs.isAndroid) {
                elements = elementTimePicker.findElements(By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.NumberPicker"));
            } else {
                elements = elementTimePicker.findElements(By.xpath("//XCUIElementTypePicker/XCUIElementTypePickerWheel"));
            }
        } catch (Exception e) {
            Log.addLog("xPath of Date Picker");
        }
        return elements;
    }

    private void clickOnDone(WebElement elementTimePicker) {
        WebElement element = null;
        try {
            if (Configs.isAndroid) {
                element = elementTimePicker.findElement(By.xpath("//android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button"));
            } else {
                element = elementTimePicker.findElement(By.xpath("//XCUIElementTypeButton"));
            }
        } catch (Exception e) {
            Log.addLog("xPath of Date Picker - button");
        }

        if (element != null)
            element.click();
    }

    /**
     * 0 = month, 1 = day, 2 = year
     */
    private String getTimePickerCurrent(WebElement elementTimePicker, int position) {
        WebElement monthElement = getDatePickerElements(elementTimePicker).get(position);
        if (monthElement != null) {
            String text = "";
            try {
                if (Configs.isAndroid) {
                    text = monthElement.findElement(By.xpath("//android.widget.EditText")).getText();
                } else {

                }
            } catch (Exception e) {
                Log.addLog("xPath of Time Picker - " + position);
            }
            return text;
        } else {
            Log.addLog("Error with timePicker - " + position);
            return "Error - " + position;
        }
    }

    /**
     * New DatePicker, TimePicker
     * Created by: Chaule
     */
    public void setAndDatePicker(WebElement elementDatePicker, String yyyy, String mm, String dd) {
        List<WebElement> elements = getAndDatePickerElements(elementDatePicker);
        // year
        try {
            WebElement year = elements.get(2);
            String currentYear = year.getText();
            if (!currentYear.equals(yyyy)) {
                year.click();
                driver.getKeyboard().sendKeys(yyyy);
                year.click();
            } else {
                Log.addLog("Year is current year");
            }
        } catch (Exception e) {
            Log.addLog("Missing year element");
        }

        // month
        try {
            WebElement month = elements.get(0);
            String currentMonth = month.getText();
            mm = parserCurrentMonth(mm);
            if (!currentMonth.equals(mm)) {
                month.click();
                driver.getKeyboard().sendKeys(mm);
                month.click();
            } else {
                Log.addLog("Month is current month");
            }
        } catch (Exception e) {
            Log.addLog("Missing month element");
        }

        // day
        try {
            WebElement day = elements.get(1);
            String currentDay = day.getText();
            if (!currentDay.equals(dd)) {
                day.click();
                driver.getKeyboard().sendKeys(dd);
                day.click();
            } else {
                Log.addLog("Day is current day");
            }
        } catch (Exception e) {
            Log.addLog("Missing day element");
        }

        androidBackKey();
    }

    public void setIOSDatePicker(WebElement elementDatePicker, String yyyy, String mm, String dd) {
        LocalDate setDate = null;
        try {
            setDate = LocalDate.of(Integer.valueOf(yyyy), Integer.valueOf(mm), Integer.valueOf(dd));
        } catch (Exception e) {
            Log.addLog("Can't convert setDate");
        }
        LocalDate currentDate = LocalDate.now();

        if (!currentDate.equals(setDate)) {
            String data = iOSDateParser(yyyy, mm, dd);
            Log.addLog("data ===> " + data);
            String currentDatePicker = elementDatePicker.getText();
            Log.addLog("current date ===> " + currentDatePicker);
            int maxLoop = 10;
            boolean isFound = false;
            while (maxLoop >= 0) {
                if (!currentDatePicker.equals(data)) {
                    scrollInElements(elementDatePicker, Direction.BOTTOM, 0.5, 0.6);
                    try {
                        Thread.sleep(Configs.ServerSleep);
                    } catch (Exception e) {
                        Log.addLog(e.getMessage());
                    }
                }
                currentDatePicker = elementDatePicker.getText();
                Log.addLog("current date (" + maxLoop + "): " + currentDatePicker);
                if (currentDatePicker.equals(data) || isFound) {
                    Log.addLog("===> correctly!!!");
                    break;
                } else {
                    maxLoop--;
                }
            }
//            elementDatePicker.sendKeys(data);
        } else {
            Log.addLog("Current date is correct, nothing change");
        }
    }

    private String iOSDateParser(String yyyy, String mm, String dd) {
        String data = "";
        LocalDate date = null;
        try {
            date = LocalDate.of(Integer.valueOf(yyyy), Integer.valueOf(mm), Integer.valueOf(dd));
        } catch (Exception e) {
            Log.addLog("Can't convert setDate");
        }
        DayOfWeek day = date.getDayOfWeek();

        // dayOfWeek
        String dayOfWeek = "";
        switch (day) {
            case FRIDAY:
                dayOfWeek = "Th 6";
                break;
            case THURSDAY:
                dayOfWeek = "Th 5";
                break;
            case WEDNESDAY:
                dayOfWeek = "Th 4";
                break;
            case TUESDAY:
                dayOfWeek = "Th 3";
                break;
            case MONDAY:
                dayOfWeek = "Th 2";
                break;
            case SATURDAY:
                dayOfWeek = "Th 7";
                break;
            case SUNDAY:
            default:
                dayOfWeek = "CN";
                break;
        }

        int month = Integer.valueOf(mm);
        mm = "thg " + month;

        int day2 = Integer.valueOf(dd);
        dd = String.valueOf(day2);

        data = dayOfWeek + ", " + dd + " " + mm;
        return data;
    }

    public void setAndTimePicker(WebElement elementTimePicker, String hh, String mm) {
        List<WebElement> elements = getAndDatePickerElements(elementTimePicker);
        // hh
        try {
            WebElement hour = elements.get(0);
            String currentHour = hour.getText();
            if (!currentHour.equals(hh)) {
                hour.click();
                driver.getKeyboard().sendKeys(hh);
                hour.click();
            } else {
                Log.addLog("Hour is current hour");
            }
        } catch (Exception e) {
            Log.addLog("Missing hour element");
        }

        // mm
        try {
            WebElement min = elements.get(1);
            String currentMin = min.getText();
            if (!currentMin.equals(mm)) {
                min.click();
                driver.getKeyboard().sendKeys(mm);
                min.click();
            } else {
                Log.addLog("Min is current min");
            }
        } catch (Exception e) {
            Log.addLog("Missing minute element");
        }
    }

    public void setIOSHourPicker(WebElement elementTimePicker, String hh) {
        elementTimePicker.sendKeys(hh);
    }

    private List<WebElement> getAndDatePickerElements(WebElement elementDatePicker) {
        List<WebElement> elements = null;
        try {
            elements = elementDatePicker.findElements(By.id("android:id/numberpicker_input"));
        } catch (Exception e) {
            Log.addLog("id of And Picker Elements");
        }
        return elements;
    }

    private String parserCurrentMonth(String mm) {
        String month = "";
        switch (mm) {
            case "01":
                month = "Jan";
                break;
            case "02":
                month = "Feb";
                break;
            case "03":
                month = "Mar";
                break;
            case "04":
                month = "Apr";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "Jun";
                break;
            case "07":
                month = "Jul";
                break;
            case "08":
                month = "Aug";
                break;
            case "09":
                month = "Sep";
                break;
            case "10":
                month = "Oct";
                break;
            case "11":
                month = "Nov";
                break;
            case "12":
                month = "Dec";
                break;
            default:
                Log.addLog("Error your month, please check");
                break;
        }
        return month;
    }

    /**
     * These functions use to set Dropdown list object
     * Created by: Chaule
     */
    public void setDropdownValue(List<WebElement> elements, String xPathChildElement, String value) {
        WebElement element = null;

        if (Configs.isAndroid) {
            for (int i = 0; i < elements.size(); i++) {
                element = getElement(elements, i);

                if (element != null) {
                    String elementText = "";
                    if (xPathChildElement.equals("")) {
                        elementText = element.getText();
                    } else {
                        elementText = element.findElement(By.xpath(xPathChildElement)).getText();
                    }

                    if (elementText.toLowerCase().equals(value.toLowerCase())) {
                        if (xPathChildElement.equals("")) {
                            element.click();
                        } else {
                            element.findElement(By.xpath(xPathChildElement)).click();
                        }
                    } else {
                        Log.addLog("element: " + elementText);
                    }
                }
            }
        } else {
            for (int i = 0; i < elements.size(); i = i + 2) {
                element = getElement(elements, i);

                if (element != null) {
                    String elementText = "";
                    try {
                        if (xPathChildElement.equals("")) {
                            elementText = element.getText();
                        } else {
                            elementText = element.findElement(By.xpath(xPathChildElement)).getText();
                        }
                    } catch (Exception e) {
                        Log.addLog("iOS don't render this element");
                    }
//                    Log.addLog("elementText (" + i + "): " + elementText);

                    if (!elementText.equals("")) {
                        if (elementText.toLowerCase().equals(value.toLowerCase())) {
                            if (xPathChildElement.equals("")) {
                                element.click();
                            } else {
                                element.findElement(By.xpath(xPathChildElement)).click();
                            }
                        } else {
                            Log.addLog("element: " + elementText);
                        }
                    }
                }
            }
        }
    }

    public String getValueInList(List<WebElement> elements, String xPathChildElement, int position) {
        WebElement element = elements.get(position);

        if (element != null) {
            String text = "";
            try {
                text = element.findElement(By.xpath(xPathChildElement)).getText();
            } catch (Exception e) {
                Log.addLog("Missing xPath of " + xPathChildElement);
            }
            return text;
        } else {
            Log.addLog("Missing element at " + position);
            return "Missing element at " + position;
        }
    }

    public String getValueInList(List<WebElement> elements, String xPathChildElementWantToGet, String xPathChildElementWantToFind, String valueOfxPathWantToFind) {
        WebElement element = null;
        String text = "";
        for (int i = 0; i < elements.size(); i++) {
            element = getElement(elements, i);

            if (element != null) {
                String elementText = element.findElement(By.xpath(xPathChildElementWantToFind)).getText();
                if (elementText.toLowerCase().equals(valueOfxPathWantToFind.toLowerCase())) {
                    text = element.findElement(By.xpath(xPathChildElementWantToGet)).getText();
                }
            }
        }
        return text;
    }

    private WebElement getElement(List<WebElement> elements, int position) {
        return elements.get(position);
    }

    public void clickOnElementInList(List<WebElement> elements, String xPathChildElement, int position) {
        WebElement element = elements.get(position);

        if (element != null) {
            try {
                element.findElement(By.xpath(xPathChildElement)).click();
            } catch (Exception e) {
                Log.addLog("Missing xPath of " + xPathChildElement);
            }
        } else {
            Log.addLog("Missing element at " + position);
        }
    }

    public void clickOnElementInList(List<WebElement> elements, String xPathChildElementWantToClick, String xPathChildElementWantToFind, String valueOfxPathWantToFind) {
        WebElement element = null;

        for (int i = 0; i < elements.size(); i++) {
            element = getElement(elements, i);

            if (element != null) {
                String elementText = element.findElement(By.xpath(xPathChildElementWantToFind)).getText();

                if (elementText.toLowerCase().equals(valueOfxPathWantToFind.toLowerCase())) {
                    element.findElement(By.xpath(xPathChildElementWantToClick)).click();
                    break;
                }
            }
        }
    }

    public void selectAllTextBox(WebElement element, String error) {
        try {
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().startsWith("mac")) {
                element.sendKeys(Keys.COMMAND + "A");
            } else {
                element.sendKeys(Keys.CONTROL + "A");
            }
        } catch (Exception e) {
            Log.addLog(error);
        }
    }
    public void clearTextBox(WebElement element, String error) {
        try {
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().startsWith("mac")) {
                element.sendKeys(Keys.COMMAND + "A");
            } else {
                element.sendKeys(Keys.CONTROL + "A");
            }
            element.sendKeys(Keys.DELETE);
        } catch (Exception e) {
            Log.addLog(error);
        }
    }

    /**
     * These methods use for commandLine mode
     */
    private static AppiumDriver setUpAndroid() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
//        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true"); // Appium 2. don't use this capabilities
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "500");
        capabilities.setCapability("nativeWebScreenshot", true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2"); // UIAutomator2, uiautomator2
        capabilities.setCapability("appium:settings[snapshotMaxDepth]", 100); // xử lý cho Native app
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Configs.isNotReset);
        if (Configs.isEmulator) {
            Log.addLog("- Run with Emulator - Mobile");
            device = Devices.Emulator;
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getName());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getVersion());

            capabilities.setCapability(MobileCapabilityType.UDID, device.getUdid()); //Configs.UDID
        } else {
            Log.addLog("- Run with Real Mobile device");
            device = Configs.AndroidPhone;
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getName());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getVersion());

            capabilities.setCapability(MobileCapabilityType.UDID, device.getUdid()); //Configs.UDID
        }

        if (Configs.isMobileWeb) {
            Log.addLog("- Check Finan - Mobile Web");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        } else {
            Log.addLog("- Check Finan Mobile App");
            apps = Apps.VIKKI_SIT;
            capabilities.setCapability("appPackage", apps.getAppPackage());
            capabilities.setCapability("appActivity", apps.getAppActivity());
        }

        driver = null;
        try {
            if (Configs.isStartAppiumServer) {
                driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
            } else {
                driver = new AndroidDriver(new URL("http://0.0.0.0:4723/"), capabilities); // Appium 2. don't use path /wd/hub
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }

    private static AppiumDriver setUpIOS() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability("xcodeSigningId", Configs.xcodeSigningId);
        capabilities.setCapability("xcodeOrgId", Configs.xcodeOrgId);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability("appium:shouldTerminateApp", true); // add this capability if driver.quit() is not work
//        capabilities.setCapability("autoWebview",true);
        if (Configs.isEmulator) {
            Log.addLog("- Run with Simulator");
//            device = Devices.Simulator;
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getName());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getVersion());
        } else {
            Log.addLog("- Run with Real Mobile device");
            device = Configs.iPhone;
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getName());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getVersion());
            capabilities.setCapability(MobileCapabilityType.UDID, device.getUdid());
        }

        if (Configs.isMobileWeb) {
            Log.addLog("- Check Finan - Mobile Web");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        } else {
            Log.addLog("- Check Finan Mobile App");
            apps = Apps.VIKKI_SIT;
            capabilities.setCapability("bundleId", apps.getBundleId());
        }

        driver = null;
        try {
            driver = new IOSDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }

    public static void setUpCmdLine() throws Exception {
        // start appium server
        if (Configs.isStartAppiumServer) {
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                    .usingDriverExecutable(new File("/usr/local/bin/node"))
                    .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                    .usingPort(4723).withIPAddress("127.0.0.1")
                    .withArgument(() -> "--base-path", "/wd/hub") //--base-path or -pa
                    .withEnvironment(System.getenv()));
            service.start();

            Log.addLog("Appium server started");
        }

        if (Configs.isStartDevice) {
            if (Configs.isAndroid) {
                try {
                    setUpAndroid();
                    Log.addLog("Started Android App");
                } catch (Exception e) {
                    Log.addLog("Can't start Android App, please check - " + e.getMessage());
                }
            } else {
                Log.addLog("Start iOS App");
                setUpIOS();
            }

            if (Configs.isMobileWeb) {
                driver.get(Links.DOMAIN);
            }
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            screenshot = new Screenshot(driver);

            // upload to S3
            if (Configs.isUploadFile) {
                FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "/resources/aws.properties");
                Properties properties = new Properties();
                properties.setProperty("aws_access_key_id", Configs.aws_access_key_id);
                properties.setProperty("aws_secret_access_key", Configs.aws_secret_access_key);
                properties.setProperty("aws_namecard_bucket", Configs.aws_namecard_bucket);
                properties.setProperty("aws_url_return_prefix", Configs.aws_url_return_prefix);
                if (Configs.isAndroid) {
                    properties.setProperty("aws_url_return_env_path", "auto-test-app-output/android/");
                    properties.setProperty("aws_url_return_log_path", "auto-test-app-output/android/log/");
                    properties.setProperty("aws_url_return_img_path", "auto-test-app-output/android/img/");
                } else {
                    properties.setProperty("aws_url_return_env_path", "auto-test-app-output/ios/");
                    properties.setProperty("aws_url_return_log_path", "auto-test-app-output/ios/log/");
                    properties.setProperty("aws_url_return_img_path", "auto-test-app-output/ios/img/");
                }
                properties.store(outputStream, null);
            }

            Thread.sleep(Configs.ServerDeepSleep);
        }
    }

    public static void setupReportCmdLine(String testName) {
        String reportConfigFile = System.getProperty("user.dir") + "/resources/extent-config.xml";

        uploadFileName = testName + "_" + Generator.currentDate + ".html";
        uploadPath = System.getProperty("user.dir") + "/test-output/reportCML/" + uploadFileName;

        extent = new ExtentReports(uploadPath, true);
        extent.addSystemInfo("Host Name", "SBH")
                .addSystemInfo("Environment", apps.getEnv())
                .addSystemInfo("User Name", Setting.author.getSlackID());
        extent.loadConfig(new File(reportConfigFile));
        logFileName = Log.addLog(testName, "Start Test: " + testName);
        logFilePath = System.getProperty("user.dir") + "/test-output/log/" + logFileName;

        // Screenshot
        screenshot = new Screenshot(driver);
    }

    public static void endTestCmdLine(LogStatus logStatus, String testName) {
        if (logStatus == LogStatus.FAIL)
            isPass = false;
        if (extent != null) {
            extent.endTest(logger);
        }

        if (driver != null)
            driver.quit();

        extent.flush();
        extent.close();
        Log.addLog("********************** END TEST ************************\n");

        // stop appium server
        if (service != null) {
            service.stop();
            System.out.println("Appium server stopped");
        }

        // upload to S3 - log
        if (Configs.isUploadFile) {
            boolean isUploaded = false;
            int retry = 10;
            while (!isUploaded) {
                try {
                    Thread.sleep(Configs.ServerDeepSleep);
                } catch (Exception e) {
                    Log.addLog("Error when system waiting file to upload");
                }

                isUploaded = FileUploadS3.uploadLogFileToS3(logFileName, logFilePath);

                if (retry <= 1)
                    break;
                retry--;
            }

            if (isUploaded) {
                try {
                    logUrl = FileUploadS3.getLogFileFromS3(logFileName);
                    Log.addLog("Uploaded log file to S3: " + logUrl);
                } catch (Exception e) {
                    Log.addLog("Can't get logUrl from S3");
                }
            }

//            Log.addLog("Debug log url: " + logUrl);
        }

        // upload to S3 - report
        if (Configs.isUploadFile) {
            boolean isUploaded = false;
            int retry = 10;
            while (!isUploaded) {
                try {
                    Thread.sleep(Configs.ServerDeepSleep);
                } catch (Exception e) {
                    Log.addLog("Error when system waiting file to upload");
                }

                isUploaded = FileUploadS3.uploadFileToS3(uploadFileName, uploadPath);

                if (retry <= 1)
                    break;
                retry--;
            }

            if (isUploaded) {
                try {
                    reportUrl = FileUploadS3.getFileFromS3(uploadFileName);
                    Log.addLog("Uploaded report file to S3: " + reportUrl);
                } catch (Exception e) {
                    Log.addLog("Can't get reportUrl from S3");
                }
            }
//            Log.addLog("Debug url: " + reportUrl);
        }

        // hook to slack
        if (Configs.isHookToSlack) {
            SlackIncomingWebhook.postEndRequest("Test scenario: " + testName, reportUrl, logUrl, isPass);
        }
    }
}
