package com.seniclass.server.domain.student.dto.response;

import com.seniclass.server.domain.student.domain.LectureQna;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 QnA 응답", name = "Student.LectureQnaResponse")
public record LectureQnaResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "QnA ID", example = "1") Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 ID", example = "1") Long studentId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 이름", example = "김학생") String studentName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID", example = "1") Long lectureId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 이름", example = "Java 프로그래밍 기초") String lectureName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "질문 내용", example = "자바의 다형성에 대해 자세히 설명해주세요.") String question,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "답변 내용", example = "다형성은 하나의 객체가...") String answer,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "답변 여부", example = "true") boolean isAnswered,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "질문 생성일시", example = "2023-12-01T10:00:00")
                LocalDateTime questionCreatedAt,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "마지막 수정일시", example = "2023-12-01T11:00:00")
                LocalDateTime lastModifiedAt) {
    public static LectureQnaResponse from(LectureQna qna) {
        return new LectureQnaResponse(
                qna.getId(),
                qna.getStudent().getId(),
                qna.getStudent().getName(),
                qna.getLecture().getId(),
                qna.getLecture().getName(),
                qna.getQuestion(),
                qna.getAnswer(),
                qna.getAnswer() != null,
                qna.getCreatedDt(),
                qna.getUpdatedDt());
    }
}
