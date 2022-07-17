package com.chotot.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvConfig {
  private String browserType;
  private String baseUrl;
  private int timeoutInSecond;
  private String driverMode;
  private boolean headlessMode;
  private String seleniumGridHubUrl;
  private String downloadDir;
  private String screenshotDir;
  private String reportDir;
}
