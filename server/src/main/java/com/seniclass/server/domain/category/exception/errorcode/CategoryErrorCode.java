package com.seniclass.server.domain.category.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode implements BaseErrorCode {
    // 400 Bad Request
    INVALID_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "카테고리 이름이 올바르지 않습니다."),
    INVALID_CATEGORY_DEPTH(HttpStatus.BAD_REQUEST, "카테고리 깊이가 올바르지 않습니다."),

    // 404 Not Found
    MAIN_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "메인 카테고리를 찾을 수 없습니다."),
    SUB_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "서브 카테고리를 찾을 수 없습니다."),

    // 409 Conflict
    CATEGORY_NAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 카테고리 이름입니다."),

    // 422 Unprocessable Entity
    CATEGORY_HAS_DEPENDENCIES(HttpStatus.UNPROCESSABLE_ENTITY, "하위 카테고리가 존재하여 삭제할 수 없습니다."),

    // 500 Internal Server Error
    CATEGORY_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카테고리 처리 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
