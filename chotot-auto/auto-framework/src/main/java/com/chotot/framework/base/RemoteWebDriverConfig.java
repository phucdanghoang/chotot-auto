package com.chotot.framework.base;


import com.chotot.framework.enums.BrowserType;
import com.chotot.framework.enums.DriverMode;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class RemoteWebDriverConfig {
    BrowserType browserType;
    TestDevice testDevice;
    DriverMode driverMode;
    boolean isHeadless;
}
