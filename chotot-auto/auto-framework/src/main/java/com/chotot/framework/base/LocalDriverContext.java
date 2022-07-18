package com.chotot.framework.base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class LocalDriverContext {

    private static final Logger logger = LogManager.getLogger(LocalDriverContext.class);
    private static final ThreadLocal<WebDriver> remoteWebDriverThreadLocal = new ThreadLocal<>();

    private LocalDriverContext() {
        throw new IllegalStateException("LocalDriverContext class can't create an object");
    }

    public static WebDriver getRemoteWebDriver() {
        return remoteWebDriverThreadLocal.get();
    }

    public static void setRemoteWebDriver(WebDriver remoteDriverThreadLocal) {
        remoteWebDriverThreadLocal.set(remoteDriverThreadLocal);
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " - Set webDriver instance to ThreadLocal...");
    }

    public static void removeRemoteWebDriver() {
        remoteWebDriverThreadLocal.remove();
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " - Remove webDriver instance to ThreadLocal...");
    }

}