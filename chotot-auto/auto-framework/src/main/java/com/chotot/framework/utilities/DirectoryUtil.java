package com.chotot.framework.utilities;

import java.io.File;

public class DirectoryUtil {

  public static String getRootDir() {
    return System.getProperty("user.dir");
  }

  public static String getResourceDir() {
    return getRootDir() + "/src/main/resources/";
  }

  public static String getTestResourceDir() {
    return getRootDir() + "/src/test/resources/";
  }

  public static void deleteOldReportFiles(String filePath) {
    String outputPath = System.getProperty("user.dir") + "/" + filePath;
    String reportsDir = outputPath.substring(0, outputPath.lastIndexOf("/"));
    File dir = new File(reportsDir);
    File fileReport = new File(outputPath);
    if (!dir.exists()) {
      dir.mkdirs();
    } else {
      fileReport.delete();
    }
  }

}
