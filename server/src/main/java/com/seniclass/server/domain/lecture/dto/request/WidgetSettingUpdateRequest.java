package com.seniclass.server.domain.lecture.dto.request;

import com.seniclass.server.domain.lecture.dto.WidgetSpec;

import java.util.LinkedHashMap;

public record WidgetSettingUpdateRequest(
        LinkedHashMap<Long, WidgetSpec> widgetSettings
        ) {}
