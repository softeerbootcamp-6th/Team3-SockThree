package com.seniclass.server.domain.auth.enums;

public enum UserRole {
  STUDENT("STUDENT"),
  TEACHER("TEACHER"),
  ADMIN("ADMIN");

  private final String value;

  UserRole(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static UserRole fromValue(String value) {
    for (UserRole role : UserRole.values()) {
      if (role.value.equals(value)) {
        return role;
      }
    }
    throw new IllegalArgumentException("Unknown role: " + value);
  }
}
