package com.seniclass.server.domain.widget.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "QnA 위젯 응답")
public record QnaWidgetResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "최근 질문 목록") List<QnaItem> recentQuestions, @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "내 질문 목록") List<QnaItem> myQuestions) {
    public static QnaWidgetResponse of(List<QnaItem> recentQuestions, List<QnaItem> myQuestions) {
        return new QnaWidgetResponse(recentQuestions, myQuestions);
    }

    @Schema(description = "QnA 항목")
    public record QnaItem(
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "QnA ID") Long qnaId,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "질문") String question,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "답변") String answer,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 이름") String studentName,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 이름") String lectureName,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "생성일") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "내 질문 여부") boolean isMyQuestion) {}
}
