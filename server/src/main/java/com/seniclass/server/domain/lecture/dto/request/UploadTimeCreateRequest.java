package com.seniclass.server.domain.lecture.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record UploadTimeCreateRequest(
        @Schema(
                        description = "매주 강좌가 올라오는 요일입니다.",
                        example = "MONDAY",
                        allowableValues = {
                            "MONDAY",
                            "TUESDAY",
                            "WEDNESDAY",
                            "THURSDAY",
                            "FRIDAY",
                            "SATURDAY",
                            "SUNDAY"
                        })
                @NotNull
                DayOfWeek dayOfWeek,
        @Schema(description = "해당 요일에 강좌가 올라오는 시간입니다. HH:mm:ss 형식", example = "14:00:00") @NotNull
                LocalTime scheduledAt) {}
