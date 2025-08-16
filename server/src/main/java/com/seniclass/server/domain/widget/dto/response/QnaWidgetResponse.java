package com.seniclass.server.domain.widget.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public record QnaWidgetResponse(List<QnaItem> recentQuestions, List<QnaItem> myQuestions) {
    public static QnaWidgetResponse of(List<QnaItem> recentQuestions, List<QnaItem> myQuestions) {
        return new QnaWidgetResponse(recentQuestions, myQuestions);
    }

    public record QnaItem(
            Long qnaId,
            String question,
            String answer,
            String studentName,
            String lectureName,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
            boolean isMyQuestion) {}
}
