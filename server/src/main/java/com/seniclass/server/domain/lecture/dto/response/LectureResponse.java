package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.enums.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 상세 정보 응답 DTO", name = "Lecture.LectureResponse")
public record LectureResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID", example = "1") Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의명", example = "왕초보 골프 스윙 마스터 클래스") String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "기수", example = "1") Integer cohort,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "난이도", example = "BEGINNER") Level level,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 시작일", example = "2024-08-01") LocalDate startDate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 종료일", example = "2024-10-31") LocalDate endDate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "최대 수강 인원", example = "30") Integer maxStudent,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "수강료", example = "150000") Integer fee,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 소개", example = "골프 스윙의 기본부터 응용까지 체계적으로 배워보세요.")
                String instruction,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 상세 설명", example = "본 강의는 초보자를 위한 맞춤형 커리큘럼으로 구성되어 있습니다.")
                String description,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, 
                        description = "강의 이미지 URL or Key",
                        example = "https://aws.com/lectures/images/lecture1.jpg?signedKey=123")
                String imageURL) {

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
                lecture.getDescription(),
                null);
    }

    public static LectureResponse from(Lecture lecture, String presignedImageURL) {
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
                lecture.getDescription(),
                presignedImageURL);
    }
}
