package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.enums.Level;
import java.time.LocalDate;
import java.util.List;

public record LectureInfoWidgetResponse(
        Long lectureId,
        Integer cohort,
        List<UploadTimeResponse> uploadTimesList,
        LocalDate start,
        LocalDate endDate,
        Level level,
        Integer fee) {
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
