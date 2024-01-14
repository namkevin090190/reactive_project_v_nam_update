package utilities;

import config.Configs;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class Screenshot {
    WebDriver driver;

    public Screenshot(WebDriver driver) {
        this.driver = driver;
    }

    public void takeScreenshot(String filename) throws IOException {
        String fileName;
        if (Configs.isAndroid) {
            fileName = "AND_" + filename + "-" + Generator.currentTime + ".png";
        } else {
            fileName = "IOS_" + filename + "-" + Generator.currentTime + ".png";
        }
        String directory = Links.PATH_TO_IMG;
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(directory + fileName));
    }

    public void takeScreenshotNullTime(String filename) throws IOException {
        String fileName;
        if (Configs.isAndroid) {
            fileName = "AND_" + filename + ".png";
        } else {
            fileName = "IOS_" + filename + ".png";
        }
        String directory = Links.PATH_TO_IMG;
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(directory + fileName));
    }
}
