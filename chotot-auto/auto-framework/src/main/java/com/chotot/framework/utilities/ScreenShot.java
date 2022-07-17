package com.chotot.framework.utilities;

import com.vimalselvam.cucumber.listener.Reporter;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {

    private ScreenShot() {
        throw new IllegalStateException("ScreenShot class can't create an object");
    }

    public static void takeScreenShot(String methodName, WebDriver driver, String filePath) throws IOException {
        File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationPath = new File(filePath + methodName + ".png");
        FileUtils.copyFile(sourcePath, destinationPath);
        ExtentReport.addScreenCaptureFromPath(destinationPath.toString());
    }
}
