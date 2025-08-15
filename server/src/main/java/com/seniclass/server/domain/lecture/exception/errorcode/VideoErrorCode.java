package com.seniclass.server.domain.lecture.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VideoErrorCode implements BaseErrorCode {

    // 404 Not Found
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 동영상을 찾을 수 없습니다."),

    // 400 Bad Request
    VIDEO_UPLOAD_INVALID(HttpStatus.BAD_REQUEST, "동영상 업로드 요청이 유효하지 않습니다."),

    // 500 Internal Server Error
    VIDEO_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "동영상 업로드에 실패했습니다."),
    VIDEO_PROCESSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "동영상 처리에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
