package org.example;

import org.example.template.Document;
import org.example.template.TemplateModel;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
  
  private static final String OUTPUT_FILEPATH = Paths.get("src", "main", "resources", "tmp", "word", "document.xml").toUri().getPath();
  
  private static final TemplateEngine templateEngine;
  
  static {
    FileTemplateResolver templateResolver = new FileTemplateResolver();
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    
    templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
  }
  
  private static void renderDocx(String template, TemplateModel templateModel, String output) throws IOException {
    String tmp = Paths.get("src", "main", "resources", "tmp").toUri().getPath();
    
    FileUtils.copyFile(template, output);
    FileUtils.unzip(output, tmp);
    
    Context context = new Context();
    context.setVariables(templateModel.toMap());
    
    String renderedXml = templateEngine.process(OUTPUT_FILEPATH, context);
    
    try (FileWriter fileWriter = new FileWriter(OUTPUT_FILEPATH)) {
      fileWriter.write(renderedXml);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    FileUtils.zipDirectory(tmp, output);
    FileUtils.deleteDirectory(tmp);
  }
  
  public static void main(String[] args) throws IOException {
    String template = Paths.get("src", "main", "resources", "template.docx").toUri().getPath();
    String output = Paths.get("src", "main", "resources", "output.docx").toUri().getPath();
    
    Document documentTemplateModel = new Document(
      "John",
      "London",
      "Baker Street",
      "123 11 22 33",
      "john.example@mail.com"
    );
    
    renderDocx(template, documentTemplateModel, output);
  }
}
