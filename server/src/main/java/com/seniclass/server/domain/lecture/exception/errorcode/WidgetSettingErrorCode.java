package com.seniclass.server.domain.lecture.exception.errorcode;

import com.seniclass.server.global.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WidgetSettingErrorCode implements BaseErrorCode {

    // 400 BAD REQUEST
    INVALID_SIZE(HttpStatus.BAD_REQUEST, "위젯의 크기는 1 이상이어야 합니다."),
    INVALID_POSITION(HttpStatus.BAD_REQUEST, "위젯의 위치는 그리드를 벗어날 수 없습니다."),
    INVALID_PLACEMENT(HttpStatus.BAD_REQUEST, "위젯은 겹쳐서 배치할 수 없습니다."),
    WIDGET_SETTING_COUNT_INVALID(HttpStatus.BAD_REQUEST, "위젯 세팅 값은 6개여야 합니다."),
    WIDGET_ID_MISMATCH(HttpStatus.BAD_REQUEST, "요청한 위젯 ID와 강의 정보에 존재하는 위젯 ID 정보가 일치하지 않습니다."),


    // 409 CONFLICT
    ALREADY_EXIST(HttpStatus.CONFLICT, "이미 위젯 세팅 값이 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
