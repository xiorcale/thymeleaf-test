package org.example.template;

public class Page {
  
  private final String description;
  private final String title;
  
  public Page(String description, String title) {
    this.description = description;
    this.title = title;
  }
  
  public String getDescription() {
    return description;
  }
  
  public String getTitle() {
    return title;
  }
}
