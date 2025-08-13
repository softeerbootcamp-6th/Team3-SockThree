package com.seniclass.server.domain.lecture.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "내 강의 현황 위젯 응답")
public record MyLectureStatusWidgetResponse(
        @Schema(description = "강의 진행률", example = "50") Integer lectureProgressRate,
        @Schema(description = "강좌의 총 동영상 재생시간", example = "121") Integer totalVideoDurationHour,
        @Schema(description = "총 동영상 개수", example = "10") Integer lectureVideoCount,
        @Schema(description = "가장 가까운 과제 마감일", example = "2025-08-20T23:59:59")
                LocalDateTime assignmentDueDateTime,
        @Schema(description = "과제 이름", example = "1주차 과제") String assignmentName,
        @Schema(description = "과제 제출 여부", example = "true") Boolean isAssignmentSubmitted) {
    public static MyLectureStatusWidgetResponse of(
            Integer lectureProgressRate,
            Long totalVideoDurationHour,
            Integer lectureVideoCount,
            LocalDateTime assignmentDueDateTime,
            String assignmentName,
            Boolean isAssignmentSubmitted) {
        return new MyLectureStatusWidgetResponse(
                lectureProgressRate,
                (int) (totalVideoDurationHour / 3600),
                lectureVideoCount,
                assignmentDueDateTime,
                assignmentName,
                isAssignmentSubmitted);
    }

    public static MyLectureStatusWidgetResponse of(
            Integer lectureProgressRate, Long totalVideoDurationHour, Integer lectureVideoCount) {
        return new MyLectureStatusWidgetResponse(
                lectureProgressRate,
                (int) (totalVideoDurationHour / 3600),
                lectureVideoCount,
                null,
                null,
                null);
    }
}
