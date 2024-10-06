package org.example;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.TimeZone;

public class FreemarkerConfig {
  
  private static final Configuration cfg;
  
  static {
    cfg = new Configuration(Configuration.VERSION_2_3_33);
    
    try {
      File templateDir = new File(Paths.get("src", "main", "resources").toUri().getPath());
      cfg.setDirectoryForTemplateLoading(templateDir);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);
    cfg.setFallbackOnNullLoopVariable(false);
    cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
  }
  
  public static Configuration getInstance() {
    return cfg;
  }
}
