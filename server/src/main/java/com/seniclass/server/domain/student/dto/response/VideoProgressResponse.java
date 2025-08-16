package com.seniclass.server.domain.student.dto.response;

import com.seniclass.server.domain.student.domain.VideoProgress;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "비디오 진행 상황 응답", name = "Student.VideoProgressResponse")
public record VideoProgressResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "진행 상황 ID", example = "1") Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 ID", example = "1") Long studentId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 이름", example = "김학생") String studentName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "비디오 ID", example = "1") Long videoId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "비디오 이름", example = "1강 - Java 소개") String videoName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID", example = "1") Long lectureId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 이름", example = "Java 프로그래밍 기초") String lectureName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "챕터 이름", example = "1장 - Java 기초") String chapterName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "재생 시간 (초)", example = "120") Long playedTime,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "마지막 시청일시", example = "2023-12-01T10:00:00")
                LocalDateTime lastWatchedAt) {
    public static VideoProgressResponse from(VideoProgress progress) {
        return new VideoProgressResponse(
                progress.getId(),
                progress.getStudent().getId(),
                progress.getStudent().getName(),
                progress.getVideo().getId(),
                progress.getVideo().getName(),
                progress.getVideo().getChapter().getLecture().getId(),
                progress.getVideo().getChapter().getLecture().getName(),
                progress.getVideo().getChapter().getName(),
                progress.getPlayedTime(),
                progress.getUpdatedDt());
    }
}
