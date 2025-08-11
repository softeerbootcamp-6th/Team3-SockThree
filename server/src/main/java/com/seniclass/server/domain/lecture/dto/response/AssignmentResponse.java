package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Assignment;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record AssignmentResponse(
        Long id, String title, String content, String fileLink, LocalDate dueDate, Long lectureId) {
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
