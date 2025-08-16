package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.enums.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "강의 정보 위젯 응답")
public record LectureInfoWidgetResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID") Long lectureId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "기수") Integer cohort,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "업로드 시간 목록") List<UploadTimeResponse> uploadTimesList,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "시작일") LocalDate start,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "종료일") LocalDate endDate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "난이도") Level level,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "수강료") Integer fee) {
    public static LectureInfoWidgetResponse from(
            Lecture lecture, List<UploadTimeResponse> uploadTimesList) {
        return new LectureInfoWidgetResponse(
                lecture.getId(),
                lecture.getCohort(),
                uploadTimesList,
                lecture.getStartDate(),
                lecture.getEndDate(),
                lecture.getLevel(),
                lecture.getFee());
    }
}
