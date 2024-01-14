package utilities;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import config.Configs;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class Reports {
    /**
     * add Log with extent report status
     */
    public static void reportWithStatus(ExtentTest extentTest, boolean isPass, String message) {
        if (isPass) {
            extentTest.log(LogStatus.PASS, message);
        } else {
            extentTest.log(LogStatus.FAIL, message);
        }
        Log.addLog(message);
    }

    /**
     * add Log compare 2 strings with extent report status
     */
    public static void reportWithCompareString(ExtentTest extentTest, String expect, String actual, String message) {
        if (expect.equals(actual)) {
            extentTest.log(LogStatus.PASS, message + " ===> correctly");
        } else {
            extentTest.log(LogStatus.FAIL, message + " ===> failure");
        }
        Log.addLog(message);
    }

    public static void reportWithCompareString(ExtentTest extentTest, String expect, String actual, String message, String imgName) {
        String url, logFileName;
        imgName = imgName + "-" + Generator.currentDate;
        logFileName = imgName + ".png";

        if (Configs.isAndroid) {
            url = System.getProperty("user.dir") + "/test-output/img/AND_" + logFileName;
        } else {
            url = System.getProperty("user.dir") + "/test-output/img/IOS_" + logFileName;
        }

        if (Configs.isUploadImg) {
            // upload to S3 - img
            boolean isUploaded = false;
            int retry = 10;
            while (!isUploaded) {
                try {
                    Thread.sleep(Configs.ServerDeepSleep);
                } catch (Exception e) {
                    Log.addLog("Error when system waiting file to upload");
                }

                isUploaded = FileUploadS3.uploadImgToS3(logFileName, url);

                if (retry <= 1) break;
                retry--;
            }

            if (isUploaded) {
                try {
                    url = FileUploadS3.getImgFromS3(logFileName);
                } catch (Exception e) {
                    Log.addLog("Can't get logUrl from S3");
                }
            }
        }

        String img = " <br> <img src=\"" + url + "\" align=\"left\" width=\"350\">";

        if (expect.toLowerCase().equals(actual.toLowerCase())) {
            extentTest.log(LogStatus.PASS, message + " => correctly" + img);
        } else {
            extentTest.log(LogStatus.FAIL, message + " => failure" + img);
        }
        Log.addLog(message);
        Log.addLog("Expected result: " + expect);
        Log.addLog("Actual result: " + actual);
    }

    /**
     * add Log compare int with extent report status
     */
    public static void reportWithCompareInt(ExtentTest extentTest, int expect, int actual, String message) {
        if (expect == actual) {
            extentTest.log(LogStatus.PASS, message + " ===> correctly");
        } else {
            extentTest.log(LogStatus.FAIL, message + " ===> failure");
        }
        Log.addLog(message);
        Log.addLog("Expected result: " + expect);
        Log.addLog("Actual result: " + actual);
    }

    /**
     * add Log with check display and show on extent report
     */
    public static void reportWithIsDisplay(ExtentTest extentTest, boolean isDisplay, String message) {
        if (isDisplay) {
            extentTest.log(LogStatus.PASS, message);
        } else {
            extentTest.log(LogStatus.FAIL, message);
        }
        Log.addLog(message);
    }

    /**
     * add Log with check display and show on extent report
     */
    public static void reportWithIsDisplay(ExtentTest extentTest, boolean isDisplay, String message, String imgName) {
        String url, logFileName;
        imgName = imgName + "-" + Generator.currentDate;
        logFileName = imgName + ".png";

        if (Configs.isAndroid) {
            url = System.getProperty("user.dir") + "/test-output/img/AND_" + logFileName;
        } else {
            url = System.getProperty("user.dir") + "/test-output/img/IOS_" + logFileName;
        }

        if (Configs.isUploadImg) {
            // upload to S3 - img
            boolean isUploaded = false;
            int retry = 10;
            while (!isUploaded) {
                try {
                    Thread.sleep(Configs.ServerDeepSleep);
                } catch (Exception e) {
                    Log.addLog("Error when system waiting file to upload");
                }

                isUploaded = FileUploadS3.uploadImgToS3(logFileName, url);

                if (retry <= 1) break;
                retry--;
            }

            if (isUploaded) {
                try {
                    url = FileUploadS3.getImgFromS3(logFileName);
                } catch (Exception e) {
                    Log.addLog("Can't get logUrl from S3");
                }
            }
        }

        String img = " <br> <img src=\"" + url + "\" align=\"left\" width=\"350\">";

        if (isDisplay) {
            extentTest.log(LogStatus.PASS, message + img);
        } else {
            extentTest.log(LogStatus.FAIL, message + img);
        }
        Log.addLog(message);
    }
}
