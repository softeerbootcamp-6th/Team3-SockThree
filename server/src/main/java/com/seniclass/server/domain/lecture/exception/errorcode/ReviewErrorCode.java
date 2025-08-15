package com.seniclass.server.domain.lecture.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    // 400 Bad Request
    REVIEW_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 리뷰 요청입니다."),
    REVIEW_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 해당 강의에 대한 리뷰를 작성하셨습니다."),

    // 403 Forbidden
    REVIEW_ACCESS_DENIED(HttpStatus.FORBIDDEN, "리뷰에 대한 접근 권한이 없습니다."),
    NOT_ENROLLED_STUDENT(HttpStatus.FORBIDDEN, "수강 중인 강의에만 리뷰를 작성할 수 있습니다."),

    // 404 Not Found
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),

    // 500 Internal Server Error
    REVIEW_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "리뷰 저장에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
