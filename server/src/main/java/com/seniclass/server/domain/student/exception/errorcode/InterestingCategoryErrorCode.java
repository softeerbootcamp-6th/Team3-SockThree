package com.seniclass.server.domain.student.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InterestingCategoryErrorCode implements BaseErrorCode {
    INTERESTING_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "관심 카테고리 정보를 찾을 수 없습니다."),
    INTERESTING_CATEGORY_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 등록된 관심 카테고리입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
