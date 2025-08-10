package com.seniclass.server.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    HAS_NO_AUTHORITY(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    ;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
