package com.seniclass.server.domain.student.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VideoProgressErrorCode implements BaseErrorCode {
    // 404 Not Found
    VIDEO_PROGRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "비디오 진행 상황을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.getClass().getSimpleName();
    }
}
