package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.auth.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthContext {

  private static final String CURRENT_USER_ATTRIBUTE = "currentUser";
  private static final String USER_ID_ATTRIBUTE = "userId";
  private static final String USER_ROLE_ATTRIBUTE = "userRole";

  public static AuthenticatedUser getCurrentUser() {
    HttpServletRequest request = getCurrentRequest();
    if (request == null) {
      return null;
    }
    return (AuthenticatedUser) request.getAttribute(CURRENT_USER_ATTRIBUTE);
  }

  public static String getCurrentUserId() {
    HttpServletRequest request = getCurrentRequest();
    if (request == null) {
      return null;
    }
    return (String) request.getAttribute(USER_ID_ATTRIBUTE);
  }

  public static UserRole getCurrentUserRole() {
    HttpServletRequest request = getCurrentRequest();
    if (request == null) {
      return null;
    }
    return (UserRole) request.getAttribute(USER_ROLE_ATTRIBUTE);
  }

  public static boolean isAuthenticated() {
    return getCurrentUser() != null;
  }

  public static boolean hasRole(UserRole role) {
    UserRole currentRole = getCurrentUserRole();
    return currentRole != null && currentRole == role;
  }

  public static boolean hasAnyRole(UserRole... roles) {
    UserRole currentRole = getCurrentUserRole();
    if (currentRole == null) {
      return false;
    }

    for (UserRole role : roles) {
      if (currentRole == role) {
        return true;
      }
    }
    return false;
  }

  private static HttpServletRequest getCurrentRequest() {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return attributes != null ? attributes.getRequest() : null;
  }
}
