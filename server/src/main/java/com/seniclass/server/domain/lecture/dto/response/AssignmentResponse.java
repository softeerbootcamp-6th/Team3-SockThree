package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Assignment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@Schema(description = "과제 응답")
public record AssignmentResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "과제 ID") Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "제목") String title,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "내용") String content,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "파일 링크") String fileLink,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "마감일") LocalDate dueDate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID") Long lectureId) {
    public static AssignmentResponse from(Assignment assignment) {
        return AssignmentResponse.builder()
                .id(assignment.getId())
                .title(assignment.getName())
                .content(assignment.getInstruction())
                .fileLink(assignment.getFileLink())
                .dueDate(assignment.getDueDateTime().toLocalDate())
                .lectureId(assignment.getLecture().getId())
                .build();
    }
}
