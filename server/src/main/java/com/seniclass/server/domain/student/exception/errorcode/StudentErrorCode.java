package com.seniclass.server.domain.student.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StudentErrorCode implements BaseErrorCode {
    // 400 Bad Request
    INVALID_AGE(HttpStatus.BAD_REQUEST, "나이 값이 올바르지 않습니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "성별 값이 올바르지 않습니다."),

    // 404 Not Found
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "학생을 찾을 수 없습니다."),

    // 409 Conflict
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 등록된 이메일입니다."),

    // 기타
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
