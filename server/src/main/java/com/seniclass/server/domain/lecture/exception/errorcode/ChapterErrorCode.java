package com.seniclass.server.domain.lecture.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChapterErrorCode implements BaseErrorCode {
    CHAPTER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 챕터를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
