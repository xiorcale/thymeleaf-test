package org.example.template;

import java.util.Objects;

public record Root(
  User user
) {
  
  public Root {
    Objects.requireNonNull(user);
  }
}
