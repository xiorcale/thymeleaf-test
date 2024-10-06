package org.example.template;


import java.util.Objects;

public record User(
  String name,
  String city,
  String street,
  String phone,
  String email
) {
  
  public User {
    Objects.requireNonNull(name);
    Objects.requireNonNull(city);
    Objects.requireNonNull(street);
    Objects.requireNonNull(phone);
    Objects.requireNonNull(email);
  }
}
