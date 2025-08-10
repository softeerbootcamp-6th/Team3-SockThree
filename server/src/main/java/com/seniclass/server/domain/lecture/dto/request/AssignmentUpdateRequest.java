package com.seniclass.server.domain.lecture.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AssignmentUpdateRequest(
        @NotBlank(message = "과제 제목은 필수입니다.")
        String title,
        @NotBlank(message = "과제 내용은 필수입니다.")
        String content,
        String fileLink,
        @NotNull(message = "마감 기한은 필수입니다.")
        LocalDateTime dueDateTime
) {}