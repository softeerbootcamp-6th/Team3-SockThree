package com.seniclass.server.domain.lecture.dto;

import static com.seniclass.server.domain.lecture.enums.WidgetType.*;

import com.seniclass.server.domain.lecture.enums.WidgetSize;
import com.seniclass.server.domain.lecture.enums.WidgetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "위젯 스펙 DTO")
public record WidgetSpec(
        @Schema(
                        description = "위젯 타입",
                        example = "QNA",
                        allowableValues = {
                            "QNA",
                            "SUBMISSION",
                            "TEACHER_INFO",
                            "REVIEW",
                            "NEXT_LECTURE",
                            "STUDENTS_STATUS"
                        })
                @NotNull(message = "위젯 타입은 필수입니다.")
                WidgetType type,
        @Schema(description = "행 위치", example = "1")
                @NotNull(message = "row 값은 필수입니다.")
                @Max(value = 5, message = "row 값은 5이하여야 합니다.")
                @Min(value = 0, message = "row 값은 0 이상이어야 합니다.")
                Integer row,
        @Schema(description = "열 위치", example = "1")
                @NotNull(message = "col 값은 필수입니다.")
                @Max(value = 3, message = "row 값은 3이하여야 합니다.")
                @Min(value = 0, message = "col 값은 0 이상이어야 합니다.")
                Integer col,
        @Schema(
                        description = "위젯 크기",
                        example = "SMALL",
                        allowableValues = {"SMALL", "LARGE"})
                @NotNull(message = "widgetSize 값은 필수입니다.")
                WidgetSize widgetSize,
        @Schema(description = "표시 여부", example = "true") @NotNull(message = "표시 여부 값은 필수입니다.")
                Boolean visible) {}
