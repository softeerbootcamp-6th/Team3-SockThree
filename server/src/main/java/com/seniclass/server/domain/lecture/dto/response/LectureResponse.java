package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.enums.Level;
import java.time.LocalDate;

public record LectureResponse(
        Long id,
        String name,
        Integer cohort,
        Level level,
        LocalDate startDate,
        LocalDate endDate,
        Integer maxStudent,
        Integer fee,
        String instruction,
        String description
) {
    public static LectureResponse from(Lecture lecture) {
        return new LectureResponse(
                lecture.getId(),
                lecture.getName(),
                lecture.getCohort(),
                lecture.getLevel(),
                lecture.getStartDate(),
                lecture.getEndDate(),
                lecture.getMaxStudent(),
                lecture.getFee(),
                lecture.getInstruction(),
                lecture.getDescription()
        );
    }
}
