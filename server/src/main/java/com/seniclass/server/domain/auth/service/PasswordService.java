package com.seniclass.server.domain.auth.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

  private static final int BCRYPT_COST = 12; // 보안 강도 (4-31, 기본값 10)

  public String encodePassword(String rawPassword) {
    return BCrypt.withDefaults().hashToString(BCRYPT_COST, rawPassword.toCharArray());
  }

  public boolean matches(String rawPassword, String encodedPassword) {
    BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
    return result.verified;
  }
}
