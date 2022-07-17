package com.chotot.framework.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;
    private static String outputPath = System.getProperty("user.dir") + "/Reports/ExtentReportResults.html";
    private static ExtentHtmlReporter htmlReporter;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance("Cho Tot Testing Report");
        return extent;
    }

    public static ExtentReports createInstance(String buildName) {
        htmlReporter = new ExtentHtmlReporter(outputPath);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(buildName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(buildName);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;

    }

    public static void setOutputPath(String filePath) {
        outputPath = System.getProperty("user.dir") + filePath;

        String dirPath = outputPath.substring(0, outputPath.lastIndexOf("/"));
        File dir = new File(dirPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
