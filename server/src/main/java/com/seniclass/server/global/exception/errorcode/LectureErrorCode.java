package com.seniclass.server.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LectureErrorCode implements BaseErrorCode {
    LECTURE_INVALID(HttpStatus.BAD_REQUEST, "강좌 정보 유효성 검사에 실패했습니다."),
    LECTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 강좌 정보입니다."),
    SUB_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 서브 카테고리입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.getClass().getSimpleName();
    }
}
