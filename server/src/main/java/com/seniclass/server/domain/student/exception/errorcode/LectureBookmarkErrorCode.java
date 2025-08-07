package com.seniclass.server.domain.student.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LectureBookmarkErrorCode implements BaseErrorCode {
    LECTURE_BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "강의 북마크 정보를 찾을 수 없습니다."),
    LECTURE_BOOKMARK_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 북마크된 강의입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
