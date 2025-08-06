package com.seniclass.server.domain.lecture.dto.request;

import com.seniclass.server.domain.lecture.enums.Level;
import java.time.LocalDate;

public record LectureUpdateRequest(
        Long subCategoryId,
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
}
