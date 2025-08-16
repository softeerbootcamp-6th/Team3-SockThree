package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.UploadTime;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Schema(name = "Lecture.UploadTimeResponse", description = "업로드 시간 응답")
public record UploadTimeResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "요일", example = "월") String dayOfWeek,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "시간", example = "HH:mm") LocalTime scheduledAt) {
    public static UploadTimeResponse from(UploadTime uploadTime) {
        DayOfWeek dayOfWeek = uploadTime.getDayOfWeek();
        String dayOfWeekString =
                switch (dayOfWeek) {
                    case MONDAY -> "월";
                    case TUESDAY -> "화";
                    case WEDNESDAY -> "수";
                    case THURSDAY -> "목";
                    case FRIDAY -> "금";
                    case SATURDAY -> "토";
                    case SUNDAY -> "일";
                };
        return new UploadTimeResponse(dayOfWeekString, uploadTime.getScheduledAt());
    }
}
