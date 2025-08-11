package com.seniclass.server.domain.lecture.enums;

import lombok.Getter;

@Getter
public enum WidgetSize {
    SMALL(1),
    LARGE(3),
    ;

    final int width;

    WidgetSize(int width) {
        this.width = width;
    }
}
