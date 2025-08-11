package com.seniclass.server.domain.lecture.dto;

import static com.seniclass.server.domain.lecture.enums.WidgetType.*;

import com.seniclass.server.domain.lecture.enums.WidgetSize;
import com.seniclass.server.domain.lecture.enums.WidgetType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "위젯 스펙 DTO")
public record WidgetSpec(
        @Schema(
                        description = "위젯 타입",
                        example = "ATTENDANCE",
                        allowableValues = {
                            "QNA",
                            "SUBMISSION",
                            "TEACHER_INFO",
                            "REVIEW",
                            "NEXT_LECTURE",
                            "STUDENTS_STATUS"
                        })
                WidgetType type,
        @Schema(description = "행 위치", example = "1") Integer row,
        @Schema(description = "열 위치", example = "1") Integer col,
        @Schema(
                        description = "위젯 크기",
                        example = "SMALL",
                        allowableValues = {"SMALL", "LARGE"})
                WidgetSize widgetSize,
        @Schema(description = "표시 여부", example = "true") Boolean visible) {}
