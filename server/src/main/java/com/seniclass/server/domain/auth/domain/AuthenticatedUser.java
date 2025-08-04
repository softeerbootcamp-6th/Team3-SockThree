package com.seniclass.server.domain.auth.domain;

import com.seniclass.server.domain.auth.enums.UserRole;

public interface AuthenticatedUser {
  String getId();

  String getEmail();

  String getPassword();

  UserRole getRole();
}
