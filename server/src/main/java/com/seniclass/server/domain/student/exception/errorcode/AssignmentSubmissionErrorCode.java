package com.seniclass.server.domain.student.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AssignmentSubmissionErrorCode implements BaseErrorCode {
    // 404 Not Found
    ASSIGNMENT_SUBMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "과제 제출을 찾을 수 없습니다."),

    // 409 Conflict
    ASSIGNMENT_SUBMISSION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 제출한 과제입니다."),

    // 400 Bad Request
    ASSIGNMENT_DEADLINE_PASSED(HttpStatus.BAD_REQUEST, "과제 제출 기한이 지났습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.getClass().getSimpleName();
    }
}
