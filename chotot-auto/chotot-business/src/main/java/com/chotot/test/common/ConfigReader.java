package com.chotot.test.common;

import com.chotot.framework.config.YamlConfigReader;
import static com.chotot.framework.utilities.DirectoryUtil.getResourceDir;
import com.chotot.test.model.EnvConfig;

public class ConfigReader {

  private static final ThreadLocal<EnvConfig> context = new ThreadLocal();

  public static void setYamlConfig(String configFileName) {
    context.set(YamlConfigReader.yamlToObject(EnvConfig.class,
        getResourceDir() + "/configs/env/" + String.format("%s-config.yml", configFileName)));
  }

  public static EnvConfig getYamlConfig() {
    return context.get();
  }

}
