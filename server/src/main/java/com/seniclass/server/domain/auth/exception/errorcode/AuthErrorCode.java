package com.seniclass.server.domain.auth.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

  // 400 Bad Request
  PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

  // 401 Unauthorized
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다."),
  LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요."),

  // 404 Not Found
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

  // 409 Conflict
  EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 등록된 이메일입니다.");

  private final HttpStatus httpStatus;
  private final String message;

  @Override
  public String errorClassName() {
    return this.name();
  }
}
