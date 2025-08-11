package com.seniclass.server.domain.lecture.dto;

import com.seniclass.server.domain.lecture.enums.WidgetType;

public record WidgetSpec(
        WidgetType type,
        Integer row,
        Integer col,
        Integer height,
        Integer width,
        Boolean visible) {}
