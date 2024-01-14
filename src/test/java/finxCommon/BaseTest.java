package finxCommon;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import config.Apps;
import config.Configs;
import config.Devices;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import privates.Setting;
import utilities.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 * 1. "scroll down" on iOS can be deactivated by "Assistive Touch"
 */
public class BaseTest {
    public AppiumDriver driver;
    public Screenshot screenshot;
    public ExtentReports extent;
    public ExtentTest logger;

    public static AppiumDriverLocalService service;

    private static String className;

    private static boolean isPass = true;
    private static String reportConfigFile = System.getProperty("user.dir") + "/resources/extent-config.xml";

    // report files
    private static String reportUrl = "";
    private static String uploadPath = "";
    private static String uploadFileName = "";

    // log files
    private static String logUrl = "";
    private static String logFileName = "";
    private static String logFilePath = "";

    private Devices device;
    private Apps apps;

    private AppiumDriver setUpAndroid() {
        MutableCapabilities capabilities = new MutableCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
//        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true"); // Appium 2. don't use this capabilities
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "5000"); //
//        capabilities.setCapability("nativeWebScreenshot", true);
        capabilities.setCapability("appium:automationName", "UIAutomator2"); //MobileCapabilityType.AUTOMATION_NAME UIAutomator2, uiautomator2
//        capabilities.setCapability("appium:settings[snapshotMaxDepth]", 100); // xử lý cho Native app
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Configs.isNotReset);
        if (Configs.isEmulator) {
            Log.addLog("- Run with Emulator - Mobile");
            device = Devices.Emulator;
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getName()); //
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getVersion()); //

//            capabilities.setCapability(MobileCapabilityType.UDID, device.getUdid()); //  Configs.UDID
        } else {
            Log.addLog("- Run with Real Mobile device");
            device = Configs.AndroidPhone;
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getName());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getVersion());

            capabilities.setCapability(MobileCapabilityType.UDID, device.getUdid());
        }

        if (Configs.isMobileWeb) {
            Log.addLog("- Check FinX - Mobile Web");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        } else {
            Log.addLog("- Check FinX Mobile App");
            apps = Apps.VIKKI_SIT;
            capabilities.setCapability("appPackage", apps.getAppPackage());
            capabilities.setCapability("appActivity", apps.getAppActivity());
        }

        driver = null;
        try {
            if(Configs.isStartAppiumServer) {
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                System.out.println("Kết nối app thành công ở emulator");
            } else {
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities); // Appium 2. don't use path /wd/hub
                System.out.println("Kết nối app thành công ở emulator 22222");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }

    private AppiumDriver setUpIOS() {
        MutableCapabilities capabilities = new MutableCapabilities();
//        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability("xcodeSigningId", Configs.xcodeSigningId);
        capabilities.setCapability("xcodeOrgId", Configs.xcodeOrgId);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "500");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true); // don't get from configs for iOS
        capabilities.setCapability("appium:automationName", "xcuitest");
        capabilities.setCapability("appium:settings[snapshotMaxDepth]", 100); // xử lý cho Native app
        capabilities.setCapability("appium:shouldTerminateApp",true); // add this capability if driver.quit() is not work
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
            Log.addLog("- Check FinX - Mobile Web");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        } else {
            Log.addLog("- Check FinX Mobile App");
            apps = Apps.VIKKI_SIT;
            capabilities.setCapability("bundleId", apps.getBundleId());
        }

        driver = null;
        try {
            if(Configs.isStartAppiumServer) {
                driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            } else {
                driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); // Appium 2. don't use path /wd/hub
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        if (Configs.isStartDevice) {
            if (Configs.isAndroid) {
                try {
                    setUpAndroid();
                    Log.addLog("Started Android App");
                } catch (Exception e) {
                    Log.addLog("Can't start Android App, please check - " + e.getMessage());
                }
            } else {
                try {
                    Log.addLog("Start iOS App");
                    setUpIOS();
                } catch (Exception e) {
                    Log.addLog("Can't start iOS App, please check - " + e.getMessage());
                }
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

    @BeforeTest
    public void startReport() {
        // start appium server
    if (Configs.isStartAppiumServer) {
            System.out.println("Start - step Appium server started");
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                    .usingDriverExecutable(new File("/usr/local/bin/node"))
                    .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                    .withIPAddress("127.0.0.1").usingPort(4723)
                    .withArgument(() -> "--base-path", "/wd/hub") //--base-path or -pa
//                    .withArgument(() -> "--plugins", "images")
                    .withEnvironment(System.getenv()));
            service.start();

            System.out.println("Appium server started");
        }

        // lưu extent report ở local
        className = this.getClass().getName();
        className = className.substring(className.lastIndexOf(".") + 1);

        uploadFileName = className + "_" + Generator.currentTime + ".html";
        if (Configs.isAndroid) {
            uploadPath = System.getProperty("user.dir") + "/test-output/reports/AND_" + uploadFileName;
        } else {
            uploadPath = System.getProperty("user.dir") + "/test-output/reports/IOS_" + uploadFileName;
        }
        extent = new ExtentReports(uploadPath, true);
        extent
                .addSystemInfo("Host Name", "FinX")
                .addSystemInfo("Environment", "SIT Environment")
                .addSystemInfo("User Name", Setting.author1.toString());
        extent.loadConfig(new File(reportConfigFile));

        // ghi log
        logFileName = Log.addLog(className, "Start Test: " + className);
        logFilePath = System.getProperty("user.dir") + "/test-output/log/" + logFileName;

        // hook to Slack
        if (Configs.isHookToSlack) {
            SlackIncomingWebhook.postStartRequest("START TEST: " + className , "[" + Configs.env + "] FinX Phone");
        }
    }

    @AfterTest
    public void endReport() {
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
                } catch (Exception e) {
                    Log.addLog("Can't get logUrl from S3");
                }
            }

//            Log.addLog("Debug log url: " + logUrl);
        } else {
            logUrl = "";
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
                } catch (Exception e) {
                    Log.addLog("Can't get reportUrl from S3");
                }
            }

//            Log.addLog("Debug report url: " + reportUrl);
        } else {
            reportUrl = "";
        }

        // hook to Slack
        if (Configs.isHookToSlack) {
            SlackIncomingWebhook.postEndRequest("*END TEST:* " + className, reportUrl, logUrl, isPass);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Reports.reportWithStatus(logger, false, "Failed at " + testResult.getName());
            Reports.reportWithStatus(logger, false, "Failed by " + testResult.getThrowable());

            isPass = false;
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, "Skipped is " + testResult.getName());
        } else if (testResult.getStatus() == ITestResult.SUCCESS) {
            Reports.reportWithStatus(logger, true, testResult.getName() + " - Passed");
        }

        if (extent != null) {
            extent.endTest(logger);
        }

        if (driver != null) {
            driver.quit();
            System.out.println("Quit driver");
        }

        Log.addLog("================ NEXT TESTCASE ======================");

        // hook to Slack
        if (Configs.isHookToSlack) {
            if ((Configs.onlyHookFailed && testResult.getStatus() == ITestResult.FAILURE) || !Configs.onlyHookFailed) {
                SlackIncomingWebhook.postRequest(testResult, "Testcase - " + testResult.getName());
            }
        }
    }
}
