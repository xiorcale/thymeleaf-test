package org.example.template;


public record Document(
  String name,
  String city,
  String street,
  String phone,
  String email
) implements TemplateModel {

}
