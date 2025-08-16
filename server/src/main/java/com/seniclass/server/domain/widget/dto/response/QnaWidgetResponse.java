package com.seniclass.server.domain.widget.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "QnA 위젯 응답")
public record QnaWidgetResponse(@Schema(required = true, description = "최근 질문 목록") List<QnaItem> recentQuestions, @Schema(required = true, description = "내 질문 목록") List<QnaItem> myQuestions) {
    public static QnaWidgetResponse of(List<QnaItem> recentQuestions, List<QnaItem> myQuestions) {
        return new QnaWidgetResponse(recentQuestions, myQuestions);
    }

    @Schema(description = "QnA 항목")
    public record QnaItem(
            @Schema(required = true, description = "QnA ID") Long qnaId,
            @Schema(required = true, description = "질문") String question,
            @Schema(required = true, description = "답변") String answer,
            @Schema(required = true, description = "학생 이름") String studentName,
            @Schema(required = true, description = "강의 이름") String lectureName,
            @Schema(required = true, description = "생성일") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
            @Schema(required = true, description = "내 질문 여부") boolean isMyQuestion) {}
}
