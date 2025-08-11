package com.seniclass.server.domain.lecture.dto.request;

import com.seniclass.server.domain.lecture.dto.WidgetSpec;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.LinkedHashMap;

@Schema(description = "위젯 설정 수정 요청 DTO")
public record WidgetSettingUpdateRequest(
        @Schema(description = "위젯 설정 맵 (Key: WidgetSettingId, Value: 위젯 스펙)")
        LinkedHashMap<Long, WidgetSpec> widgetSettings
        ) {}
