package utilities;

/**
 * this class use to FinX - Log
 * created by Chaule
 */

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import config.Configs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class Log {
    public static String logFilename = "";

    /**
     * This function just only generate a record in Log file
     * Author: Chaule
     * Notes:
     */
    public static void addLog(String message) {
        try {
            String logPath;
            if (logFilename.equals("")) {
                logPath = Utils.getLogDir() + "/log_" + Generator.currentTime + ".txt";
            } else {
                logPath = Utils.getLogDir() + "/log_" + logFilename + "_" + Generator.currentDate + ".txt";
            }
            File file = new File(logPath);
            if (!file.exists()) {
                try {
                    FileWriter fw = new FileWriter(logPath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String title = "\nFinX - Test log reported.\n =============================================\n\n";
                    bw.write(title);
                    bw.close();
                    println("Logfile has been created");
                } catch (IOException e) {
                    e.printStackTrace();
                    println("[Error] When create log file, please check: " + e);
                }
            }
            String Log_line = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            Path path = Paths.get(logPath);
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            int position = lines.size();
            String log = "[" + Log_line + "]: " + message;
            lines.add(position, log);
            Files.write(path, lines, StandardCharsets.UTF_8);
            println(log);
        } catch (Exception ex) {
            print("CANNOT CREATE A DIRECTORY!");
        }
    }

    public static String addLog(String filename, String message) {
        String fullLogName = "";
        try {
            String logPath;
            if (filename.equals("")) {
                logPath = Utils.getLogDir() + "/log_" + Generator.currentTime + ".txt";
            } else {
                fullLogName = "log_" + filename + "_" + Generator.currentDate + ".txt";
                logPath = Utils.getLogDir() + "/" + fullLogName;
                logFilename = filename;
            }
            File file = new File(logPath);
            if (!file.exists()) {
                try {
                    FileWriter fw = new FileWriter(logPath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String title = "\nFinX - Test log reported.\n =============================================\n\n";
                    bw.write(title);
                    bw.close();
                    println("Logfile has been created");
                } catch (IOException e) {
                    e.printStackTrace();
                    println("[Error] When create log file, please check: " + e);
                }
            }
            String Log_line = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            Path path = Paths.get(logPath);
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            int position = lines.size();
            String log = "[" + Log_line + "]: " + message;
            lines.add(position, log);
            Files.write(path, lines, StandardCharsets.UTF_8);
            System.out.println(log);
        } catch (Exception ex) {
            print("CANNOT CREATE A DIRECTORY!");
        }
        return fullLogName;
    }

    /**
     * These functions use to add a record into Log file and Report file
     * 1.
     * 2.
     * 3.
     * Author: Chaule
     * Notes:
     */
    public static void addLog(ExtentTest extentTest, String message) {
        try {
            String logPath;
            if (logFilename.equals("")) {
                logPath = Utils.getLogDir() + "/log_" + Generator.currentTime + ".txt";
            } else {
                logPath = Utils.getLogDir() + "/log_" + logFilename + "_" + Generator.currentDate + ".txt";
            }
            File file = new File(logPath);
            if (!file.exists()) {
                try {
                    FileWriter fw = new FileWriter(logPath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String title = "\nFinX - Test log reported.\n =============================================\n\n";
                    bw.write(title);
                    bw.close();
                    println("Logfile has been created");
                } catch (IOException e) {
                    e.printStackTrace();
                    println("[Error] When create log file, please check: " + e);
                }
            }
            String Log_line = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            Path path = Paths.get(logPath);
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            int position = lines.size();
            String log = "[" + Log_line + "]: " + message;
            lines.add(position, log);
            extentTest.log(LogStatus.INFO, message);
            Files.write(path, lines, StandardCharsets.UTF_8);
            System.out.println(log);
        } catch (Exception ex) {
            print("CANNOT CREATE A DIRECTORY!");
        }
    }

    public static void addInfoLog(ExtentTest extentTest, String message) {
        extentTest.log(LogStatus.INFO, message);
        addLog(logFilename, message);
    }

    public static void addInfoLog(ExtentTest extentTest, String message, String imgName) {
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

        String img = "<br> <img src=\"" + url + "\" align=\"left\" width=\"350\">";

        extentTest.log(LogStatus.INFO, message + img);
        addLog(logFilename, message);
    }

    public static void addWarningLog(ExtentTest extentTest, String message) {
        extentTest.log(LogStatus.WARNING, message);
        addLog(message);
    }

    public static void addWarningLog(ExtentTest extentTest, String message, String imgName) {
        String url, logFileName;
        imgName = imgName + "-" + Generator.currentDate;
        logFileName = imgName + ".png";

        if (Configs.isAndroid) {
            url = System.getProperty("user.dir") + "/test-output/img/AND_" + imgName + ".png";
        } else {
            url = System.getProperty("user.dir") + "/test-output/img/IOS_" + imgName + ".png";
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

        String img = "<br> <img src=\"" + url + "\" align=\"left\" width=\"350\">";

        extentTest.log(LogStatus.WARNING, message + img);
        addLog(message);
    }

    public static void addUnknownLog(ExtentTest extentTest, String message) {
        extentTest.log(LogStatus.UNKNOWN, message);
        addLog(message);
    }

    public static void printCurrentTime() {
        System.out.println(System.currentTimeMillis());
    }

    /**
     * quickly print log to console
     *
     * @param log
     */
    public static void print(String log) {
        System.out.print("FinXLog:========" + log);
    }

    public static void print(int log) {
        System.out.print("FinXLog:========" + log);
    }

    public static void print(long log) {
        System.out.print("FinXLog:========" + log);
    }

    public static void print(char log) {
        System.out.print("FinXLog:========" + log);
    }

    public static void print(boolean log) {
        System.out.print("FinXLog:========" + log);
    }

    public static void print(Object log) {
        System.out.print("FinXLog:========" + log);
    }

    public static void println(String log) {
        System.out.println("FinXLog:========" + log);
    }

    public static void println(int log) {
        System.out.println("FinXLog:========" + log);
    }

    public static void println(long log) {
        System.out.println("FinXLog:========" + log);
    }

    public static void println(char log) {
        System.out.println("FinXLog:========" + log);
    }

    public static void println(boolean log) {
        System.out.println("FinXLog:========" + log);
    }

    public static void println(Object log) {
        System.out.println("FinXLog:========" + log);
    }
}