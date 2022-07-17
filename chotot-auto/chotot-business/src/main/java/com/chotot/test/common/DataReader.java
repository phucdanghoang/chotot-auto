package com.chotot.test.common;

import com.chotot.framework.config.YamlConfigReader;
import static com.chotot.framework.utilities.DirectoryUtil.getTestResourceDir;
import com.chotot.test.model.DataConfig;

public class DataReader {
  private static final ThreadLocal<DataConfig> context = new ThreadLocal();

  public static void setData(String dataFileName) {
    context.set(YamlConfigReader.yamlToObject(DataConfig.class,
        getTestResourceDir() + "/data/" + String.format("%s.yml", dataFileName)));
  }

  public static DataConfig getData() {
    return context.get();
  }

}
