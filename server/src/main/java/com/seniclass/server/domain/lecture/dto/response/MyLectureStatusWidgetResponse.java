package com.seniclass.server.domain.lecture.dto.response;

import java.time.LocalDateTime;

public record MyLectureStatusWidgetResponse(
        Integer LectureProgressRate,
        Integer TotalLectureLengthHour,
        Integer TotalVideoCount,
        LocalDateTime recentAssignmentDueDateTime,
        String AssignmentName,
        Boolean isAssignmentSubmitted) {
    public static MyLectureStatusWidgetResponse of(
            Integer lectureProgressRate,
            Long totalLectureDuration,
            Integer totalVideoCount,
            LocalDateTime recentAssignmentDueDateTime,
            String assignmentName,
            Boolean isAssignmentSubmitted) {
        return new MyLectureStatusWidgetResponse(
                lectureProgressRate,
                (int) (totalLectureDuration / 3600), // Convert seconds to hours
                totalVideoCount,
                recentAssignmentDueDateTime,
                assignmentName,
                isAssignmentSubmitted);
    }
}
