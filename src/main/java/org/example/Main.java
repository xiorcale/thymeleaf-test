package org.example;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.template.Root;
import org.example.template.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
  
  private static final String OUTPUT_FILEPATH = Paths.get("src", "main", "resources", "tmp", "word", "document.xml").toUri().getPath();
  private static final String TMP = Paths.get("src", "main", "resources", "tmp").toUri().getPath();
  
  private static void renderDocxFreemarker(String docxTemplate, Root root, String output) throws IOException {
    FileUtils.copyFile(docxTemplate, output);
    FileUtils.unzip(output, TMP);
    
    Template template = FreemarkerConfig.getInstance().getTemplate("tmp/word/document.xml");
    
    try (FileWriter fileWriter = new FileWriter(OUTPUT_FILEPATH)) {
      template.process(root, fileWriter);
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    
    FileUtils.zipDirectory(TMP, output);
    FileUtils.deleteDirectory(TMP);
  }
  
  public static void main(String[] args) throws IOException {
    try {
      String template = Paths.get("src", "main", "resources", "template-freemarker.docx").toUri().getPath();
      String output = Paths.get("src", "main", "resources", "output.docx").toUri().getPath();
      
      Root root = new Root(new User("John", "London", "Baker Street", "123 11 22 33", "john.example@mail.com"));
      
      renderDocxFreemarker(template, root, output);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
