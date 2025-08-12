package com.seniclass.server.domain.lecture.dto.request;

import com.seniclass.server.domain.lecture.dto.WidgetSpec;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;

@Schema(description = "위젯 설정 수정 요청 DTO")
public record WidgetSettingUpdateRequest(
        @Schema(description = "위젯 설정 맵 (Key: WidgetSettingId, Value: 위젯 스펙)")
                @NotNull(message = "위젯 세팅 값은 필수입니다.")
        @Size(min = 6, max = 6, message = "위젯 세팅은 정확히 6개여야 합니다.")
        ArrayList<WidgetSpec> widgetSpecs) {}
