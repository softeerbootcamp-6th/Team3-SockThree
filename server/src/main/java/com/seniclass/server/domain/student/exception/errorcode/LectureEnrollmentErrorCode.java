package com.seniclass.server.domain.student.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LectureEnrollmentErrorCode implements BaseErrorCode {
    LECTURE_ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "수강 등록 정보를 찾을 수 없습니다."),
    LECTURE_ENROLLMENT_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 수강 등록된 강의입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
