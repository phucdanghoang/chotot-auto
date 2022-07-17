package com.chotot.framework.base;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface DriverSetup {
    RemoteWebDriver getWebDriver(MutableCapabilities capabilities, String downloadPath, boolean isHeadless);
}
