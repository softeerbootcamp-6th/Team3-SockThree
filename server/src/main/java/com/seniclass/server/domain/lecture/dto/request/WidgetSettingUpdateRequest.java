package com.seniclass.server.domain.lecture.dto.request;

import com.seniclass.server.domain.lecture.dto.WidgetSpec;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedHashMap;

@Schema(description = "위젯 설정 수정 요청 DTO")
public record WidgetSettingUpdateRequest(
        @Schema(description = "위젯 설정 맵 (Key: WidgetSettingId, Value: 위젯 스펙)")
                @NotNull(message = "위젯 세팅 값은 필수입니다.")
                @NotEmpty(message = "위젯 세팅 값은 비어있을 수 없습니다.")
                LinkedHashMap<Long, WidgetSpec> widgetSettings) {}
