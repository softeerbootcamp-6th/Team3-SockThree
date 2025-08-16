package com.seniclass.server.domain.lecture.dto.request;

import com.seniclass.server.domain.lecture.enums.Level;
import com.seniclass.server.domain.lecture.exception.errorcode.LectureErrorCode;
import com.seniclass.server.global.exception.CommonException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Schema(description = "강의 수정 요청 DTO")
public record LectureUpdateRequest(
        @Schema(description = "하위 카테고리 ID", example = "1") @NotNull(message = "하위 카테고리 ID는 필수입니다.")
                Long subCategoryId,
        @Schema(
                        description = "난이도",
                        example = "BEGINNER",
                        allowableValues = {
                            "BEGINNER",
                            "NOVICE",
                            "INTERMEDIATE",
                            "ADVANCED",
                            "EXPERT"
                        })
                @NotNull(message = "난이도는 필수입니다.")
                Level level,
        @Schema(description = "강의 시작일", example = "2024-08-01")
                @NotNull(message = "강의 시작일은 필수입니다.")
                @FutureOrPresent(message = "강의 시작일은 오늘 또는 미래여야 합니다.")
                LocalDate startDate,
        @Schema(description = "강의 종료일", example = "2024-10-31") @NotNull(message = "강의 종료일은 필수입니다.")
                LocalDate endDate,
        @Schema(description = "최대 수강 인원", example = "30", minimum = "1", maximum = "200")
                @NotNull(message = "최대 수강 인원은 필수입니다.")
                @Min(value = 1, message = "최대 수강 인원은 1명 이상이어야 합니다.")
                @Max(value = 200, message = "최대 수강 인원은 200명 이하여야 합니다.")
                Integer maxStudent,
        @Schema(description = "강의 소개", example = "골프 스윙의 기본부터 응용까지 체계적으로 배워보세요.", maxLength = 255)
                @NotBlank(message = "강의 소개는 필수입니다.")
                @Size(max = 255, message = "강의 소개는 최대 255자입니다.")
                String instruction,
        @Schema(description = "강의 상세 설명", example = "본 강의는 초보자를 위한 맞춤형 커리큘럼으로 구성되어 있습니다.")
                @NotBlank(message = "강의 상세 설명은 필수입니다.")
                String description) {

    public void validateDateOrder() {
        if (startDate != null && endDate != null && !startDate.isBefore(endDate)) {
            throw new CommonException(LectureErrorCode.LECTURE_INVALID);
        }
    }
}
