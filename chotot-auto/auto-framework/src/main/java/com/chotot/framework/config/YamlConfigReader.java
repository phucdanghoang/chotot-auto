package com.chotot.framework.config;

import static com.chotot.framework.base.ThrowableWrapper.wrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import lombok.extern.log4j.Log4j;

@Log4j
public class YamlConfigReader {
  private YamlConfigReader() {
    throw new IllegalStateException("ConfigReader class can't create an object");
  }

  public static <T> T yamlToObject(Class<T> clazz, String filePath) {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    return wrapper(() -> mapper.readValue(new File(filePath), clazz));
  }

}
