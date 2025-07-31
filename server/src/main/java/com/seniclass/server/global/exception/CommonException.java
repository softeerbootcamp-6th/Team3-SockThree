package com.seniclass.server.global.exception;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

  private final BaseErrorCode errorCode;

  public CommonException(BaseErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
