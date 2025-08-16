package com.seniclass.server.domain.lecture.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(required = true, description = "동영상 업로드 응답")
public record VideoUploadResponse(
        @Schema(required = true, description = "생성된 비디오 ID", example = "1") Long videoId,
        @Schema(required = true, description = "S3 업로드용 Presigned URL") String uploadPresignedUrl,
        @Schema(required = true, description = "S3 저장 경로", example = "lectures/1/chapters/1/video/1/full")
                String s3Key) {}
