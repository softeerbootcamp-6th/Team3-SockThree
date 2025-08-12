package com.seniclass.server.domain.lecture.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UploadTimeErrorCode implements BaseErrorCode {

    // 404
    UPLOAD_TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "업로드 시간이 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
