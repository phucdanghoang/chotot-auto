package com.chotot.framework.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import lombok.SneakyThrows;


public class ExtentReport {

  private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal();
  private static final ThreadLocal<ExtentTest> parentTest = new ThreadLocal();
  private static ExtentReports extent;

  public ExtentReport() {
  }

  public static synchronized ExtentTest getTest() {
    return extentTestThreadLocal.get();
  }

  public synchronized static void setupReport(String outputPath) {
    ExtentManager.setOutputPath(outputPath);
    extent = ExtentManager.getInstance();

  }

  public synchronized static void logParentTest(String testName) {
    ExtentTest parent = extent.createTest(testName);
    parentTest.set(parent);
  }

  public synchronized static void logTestStart(String testName) {
    logTestStart(testName, null, (String) null);
  }

  public synchronized static void logTestStart(String testName, String desc) {
    logTestStart(testName, desc, (String) null);
  }

  public synchronized static void logTestStart(String testName, String desc, String cat) {
    logTestStart(testName, desc, new String[]{cat});
  }

  public synchronized static void logTestStart(String testName, String desc, String[] cat) {
    ExtentTest child = parentTest.get().createNode(testName, desc);
    extentTestThreadLocal.set(child);
    if (cat != null) {
      child.assignCategory(cat);
    }
  }

  public static void logTestPassed(String desc) {
    extentTestThreadLocal.get().pass(MarkupHelper.createLabel(desc, ExtentColor.GREEN));
  }

  public static void logTestFailed(String desc) {
    extentTestThreadLocal.get().fail(MarkupHelper.createLabel(desc, ExtentColor.RED));
  }

  @SneakyThrows
  public static void addScreenCaptureFromPath(String capturePath) {
    extentTestThreadLocal.get().addScreenCaptureFromPath(capturePath);
  }

  public static void logTestSkipped(String desc) {
    extentTestThreadLocal.get().skip(MarkupHelper.createLabel(desc, ExtentColor.YELLOW));
  }

  public static void logStepInfo(String desc) {
    extentTestThreadLocal.get().log(Status.INFO, desc);
  }

  public static void removeTest() {
    extent.removeTest(extentTestThreadLocal.get());
  }

  public static void generateReport() {
    extent.setSystemInfo("API_BASE_URI", System.getProperty("API_BASE_URI"));
    extent.setSystemInfo("OS version", System.getProperty("os.name"));
    extent.setSystemInfo("Host Name", System.getenv("COMPUTERNAME"));
    extent.setSystemInfo("user", System.getProperty("user.name"));
    extent.flush();
  }
}


