package com.seniclass.server.domain.student.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LectureQnaErrorCode implements BaseErrorCode {
    // 404 Not Found
    LECTURE_QNA_NOT_FOUND(HttpStatus.NOT_FOUND, "강의 질문을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.getClass().getSimpleName();
    }
}
