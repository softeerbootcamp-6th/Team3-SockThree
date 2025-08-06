package com.seniclass.server.domain.lecture.dto.request;

import com.seniclass.server.domain.lecture.enums.Level;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record LectureCreateRequest(
        Long subCategoryId,
        @NotNull Level level,
        @NotNull @PastOrPresent(message = "시작일은 오늘 또는 미래여야 합니다.") LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull @Min(1) @Max(200) Integer maxStudent,
        @NotNull @Min(0) Integer fee,
        @NotBlank String name,
        @NotBlank @Size(max = 255) String instruction,
        @NotBlank String description) {

    public void validateDateOrder() {
        if (startDate != null && endDate != null && !startDate.isBefore(endDate)) {
            throw new CommonException(LectureErrorCode.LECTURE_INVALID);
        }
    }
}
