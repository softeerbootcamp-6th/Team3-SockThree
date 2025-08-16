package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.WidgetSetting;
import com.seniclass.server.domain.lecture.enums.WidgetSize;
import com.seniclass.server.domain.lecture.enums.WidgetType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "위젯 설정 정보 응답 DTO")
public record WidgetSettingResponse(
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "위젯 설정 ID",
                        example = "1")
                Long id,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "위젯 타입",
                        example = "SUBMISSION")
                WidgetType widgetType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "행 위치", example = "1")
                Integer rowPosition,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "열 위치", example = "1")
                Integer colPosition,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "위젯 크기",
                        example = "SMALL")
                WidgetSize widgetSize,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "표시 여부",
                        example = "true")
                Boolean visible,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID", example = "1")
                Long lectureId) {
    public static WidgetSettingResponse from(WidgetSetting widgetSetting) {
        return new WidgetSettingResponse(
                widgetSetting.getId(),
                widgetSetting.getWidgetType(),
                widgetSetting.getRowPosition(),
                widgetSetting.getColPosition(),
                widgetSetting.getWidgetSize(),
                widgetSetting.getVisible(),
                widgetSetting.getLecture().getId());
    }
}
