package actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Log;
import utilities.Utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * MobileActions.java
 * 🤔 🤔 🤔
 */

public class MobileActions {
    private AppiumDriver driver;
    private TouchAction _touchAction;

    private TouchAction touchAction() {
        if (_touchAction == null) {
            _touchAction = new TouchAction(driver);
        }
        return _touchAction;
    }

    public MobileActions(AppiumDriver driver) {
        this.driver = driver;
    }

    public AndroidElement elementByID(String id) {
        AndroidElement element = null;
        try {
            element = (AndroidElement) driver.findElementById(id);
            Log.println("element has been initialized");
        } catch (Exception ex) {
            Log.println("cannot initialize a element");
        } finally {
            return element;
        }
    }

    public AndroidElement elementByXPATH(String xpath) {
        AndroidElement element = null;
        try {
            element = (AndroidElement) driver.findElementByXPath(xpath);
        } catch (Exception ex) {
            Log.addLog(ex.getMessage());
        } finally {
            return element;
        }
    }

    public void clickElementByID(String id) {
        AndroidElement element = elementByID(id);
        clickOn(element);
    }

    public void clickElementByXPATH(String xpath) {
        AndroidElement element = elementByXPATH(xpath);
        clickOn(element);
    }

    public void clickOn(AndroidElement element) {
        if (element == null) {
            Log.println("element is null -> return");
            return;
        }
        if (element.isEnabled()) {
            element.click();
            Log.println("Click to element");
        } else {
            Log.println("Wait to element enable...");
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.withMessage("Could not load element");
            element.click();
        }
    }

    public void tapOutside(AndroidElement element) {
        int screenWidth = driver.manage().window().getSize().width;
        int screenHeight = driver.manage().window().getSize().height;
        int elementMaxX = element.getLocation().x + element.getSize().width;
        int elementMaxY = element.getLocation().y + element.getSize().height;

        if (element.getLocation().x > 2) {
            int x = element.getLocation().x / 2;
            touchAction().tap(new PointOption().withCoordinates(x, screenHeight / 2)).perform();
            Log.println("Tap outside LEFT, with POINT: " + x + " - " + screenHeight / 2);
            return;
        }

        if (elementMaxX < screenWidth) {
            int x = elementMaxX + ((screenWidth - elementMaxX) / 2);
            touchAction().tap(new PointOption().withCoordinates(x, screenHeight / 2)).perform();
            Log.println("Tap outside RIGHT, with POINT: " + x + " - " + screenHeight / 2);
            return;
        }

        if (elementMaxY < screenHeight) {
            int y = elementMaxY + ((screenHeight - elementMaxY) / 2);
            touchAction().tap(new PointOption().withCoordinates(screenWidth / 2, y)).perform();
            Log.println("Tap outside BOTTOM, with POINT: " + screenWidth / 2 + " - " + y);
            return;
        }
        //TODO: define click outside TOP

        if (element.getLocation().getY() > 20) {
            int y = element.getLocation().y / 2;
            touchAction().tap(new PointOption().withCoordinates(screenWidth / 2, y)).perform();
            Log.println("Tap outside TOP, with POINT: " + screenWidth / 2 + " - " + y);
            return;
        }
    }

    /**
     * tapON will tap on screen at point of Element, it not care state of Element is Disable or Enable
     */
    public void tapOn(AndroidElement element) {
        try {
            Point point = new Point(element.getCenter().getX(), element.getCenter().getY());
            Log.println("[TAP] at" + point);
            PointOption pointOption = new PointOption().withCoordinates(point);
            touchAction().tap(pointOption).perform();

        } catch (Exception ex) {
            Log.addLog(ex.getMessage());
        }
    }

    public void tapOnPoint(Point point) {
        try {
            Log.println("[TAP] at" + point);
            PointOption pointOption = new PointOption().withCoordinates(point);
            touchAction().tap(pointOption).perform();

        } catch (Exception ex) {
            Log.addLog(ex.getMessage());
        }
    }

//    public void pressKey(char key) {
//
//        driver.pressKey(new KeyEvent(Keys.withChar(key)));
//    }

    public boolean isPresent(AndroidElement element) {

        if (element == null) {
            return false;
        }
        if (element.isEnabled()) {
            return true;
        }

        try {

            WebDriverWait wait = new WebDriverWait(driver, 2);

            if (wait.until(ExpectedConditions.visibilityOf(element)) != null) {

                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPresent(String id) {
        try {
            return driver.findElementById(id).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isEnable(String id) {
        try {
            Log.println("checking element is present...");
            AndroidElement element = (AndroidElement) driver.findElementById(id);

            boolean isEnable = element.isEnabled();
            Log.addLog("isEnable: " + isEnable);
            if (isEnable) {
                return true;
            }

            try {
                WebDriverWait wait = new WebDriverWait(driver, 2);
                if (wait.until(ExpectedConditions.visibilityOf(element)) != null) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    /**
     * send key to element
     */
    public void sendKeys(AndroidElement element, String message) {
        if (element.isEnabled()) {
            element.sendKeys(message);
        } else {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(message);
        }
    }

    /**
     * TouchAction
     */
    public void swipeElementByPercentage(AndroidElement element, Direction direction) {
        double endXPercent = 0.9;
        int startX, startY, endX, endY;

        int screenWidth = driver.manage().window().getSize().width;
        int screenHeight = driver.manage().window().getSize().height;

        switch (direction) {
            case TOP:
                startX = element.getCenter().getX();
                startY = element.getSize().getHeight();

                endX = startX;
                endY = screenHeight - (int) (screenHeight * endXPercent);
                break;
            case RIGHT:
                startX = element.getCenter().getX();
                startY = element.getCenter().getY();

                endX = (int) (screenWidth * endXPercent);
                endY = startY;
                break;
            case LEFT:
                startX = element.getSize().getWidth();
                startY = element.getCenter().getY();

                endX = screenWidth - (int) (screenWidth * endXPercent);
                endY = startY;
                break;

            default:
                startX = element.getCenter().getX();
                startY = element.getLocation().getY();

                endX = startX;
                endY = (int) (screenHeight * endXPercent);
                break;
        }

        Point startPoint = new Point(startX, startY);
        PointOption startPointOption = new PointOption().withCoordinates(startPoint);

        Point endPoint = new Point(endX, endY);
        PointOption endPointOption = new PointOption().withCoordinates(endPoint);

        WaitOptions waitOptions = new WaitOptions().withDuration(Duration.ofMillis(500));

        Log.addLog("Start Point: " + startPoint + " and End point: " + endPoint);
        touchAction().press(startPointOption).waitAction(waitOptions).moveTo(endPointOption).waitAction().release().perform();

    }

    /**
     * @param element
     * @param direction
     * @param endXPercent Default is 0.9
     */
    public void swipeElementByPercentage(AndroidElement element, Direction direction, double endXPercent) {

        int startX, startY, endX, endY;

        int screenWidth = driver.manage().window().getSize().width;
        int screenHeight = driver.manage().window().getSize().height;

        switch (direction) {
            case TOP:
                startX = element.getCenter().getX();
                startY = element.getSize().getHeight();

                endX = startX;
                endY = screenHeight - (int) (screenHeight * endXPercent);
                break;
            case RIGHT:
                startX = element.getLocation().getX();
                startY = element.getLocation().getY();

                endX = (int) (screenWidth * endXPercent);
                endY = startY;
                break;
            case LEFT:
                startX = element.getSize().getWidth();
                startY = element.getCenter().getY();

                endX = screenWidth - (int) (screenWidth * endXPercent);
                endY = startY;
                break;

            default:
                startX = element.getCenter().getX();
                startY = element.getLocation().getY();

                endX = startX;
                endY = (int) (screenHeight * endXPercent);
                break;
        }

        Point startPoint = new Point(startX, startY);
        PointOption startPointOption = new PointOption().withCoordinates(startPoint);

        Point endPoint = new Point(endX, endY);
        PointOption endPointOption = new PointOption().withCoordinates(endPoint);

        WaitOptions waitOptions = new WaitOptions().withDuration(Duration.ofMillis(2000));

        touchAction().press(startPointOption).waitAction(waitOptions).moveTo(endPointOption).waitAction().release().perform();

    }

    /**
     * @param fromPoint: input x and y
     * @param toPoint
     */
    public void swipeOnScreen(Point fromPoint, Point toPoint) {

        WaitOptions waitOptions = new WaitOptions().withDuration(Duration.ofMillis(200));
        touchAction().press(new PointOption().withCoordinates(fromPoint)).waitAction(waitOptions).moveTo(new PointOption().withCoordinates(toPoint)).waitAction().release().perform();
    }

    public void swipeOnScreen(Direction direction) {
        final int offset = 100;
        final double endPercent = 0.9;
        int startX, startY, endX, endY;

        int screenWidth = driver.manage().window().getSize().width - offset;
        int screenHeight = driver.manage().window().getSize().height - offset;

        switch (direction) {
            case TOP:
                startX = screenWidth / 2;
                startY = screenHeight - offset;

                endX = startX;
                endY = screenHeight - (int) (screenHeight * endPercent);
                break;
            case RIGHT:
                startX = offset;
                startY = screenHeight / 2;

                endX = (int) (screenWidth * endPercent);
                endY = startY;
                break;
            case LEFT:
                startX = screenWidth - offset;
                startY = screenHeight / 2;

                endX = screenWidth - (int) (screenWidth * endPercent);
                endY = startY;
                break;

            default:
                startX = screenWidth / 2;
                startY = offset;

                endX = startX;
                endY = (int) (screenHeight * endPercent);
                break;
        }

        Point startPoint = new Point(startX, startY);
        PointOption startPointOption = new PointOption().withCoordinates(startPoint);

        Point endPoint = new Point(endX, endY);
        PointOption endPointOption = new PointOption().withCoordinates(endPoint);

        Log.addLog("Swipe from: " + startPoint + " to: " + endPoint);
        WaitOptions waitOptions = new WaitOptions().withDuration(Duration.ofMillis(2000));

        touchAction().press(startPointOption).waitAction(waitOptions).moveTo(endPointOption).waitAction().release().perform();
    }

    private static int CURRENT_PAGE = 0;
    private static final int MAX_PAGE = 10;

    public AndroidElement scrollTo(String id) {
        int screenWidth = driver.manage().window().getSize().width;
        int screenHeight = driver.manage().window().getSize().height;

        Point startPoint = new Point(screenWidth / 2, screenHeight);
        Point endPoint = new Point(screenWidth / 2, 0);

        while (isFoundElement(id) && CURRENT_PAGE < MAX_PAGE) {
            CURRENT_PAGE++;
            swipeOnScreen(startPoint, endPoint);
        }

        return (AndroidElement) driver.findElementById(id);
    }

    private boolean isFoundElement(String id) {
        try {
            AndroidElement element = (AndroidElement) driver.findElementById(id);
            if (element == null) {
                return false;
            }

            return element.isEnabled();

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * @param imageName
     * @return: AbsolutePath of Image. (Image will be saved at folder "Report/.../Images")
     */
    public String takeScreenshot(String imageName) {
        String pathName = Utils.createDir(Utils.getReportDir() + "/Images");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File image = new File(pathName, imageName + ".png");
        try {
            FileUtils.copyFile(scrFile, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image.getAbsolutePath();
    }


}
