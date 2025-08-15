package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.VideoUploadRequest;
import com.seniclass.server.domain.lecture.dto.response.VideoUploadResponse;
import com.seniclass.server.domain.lecture.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Video", description = "동영상 관리 API")
@RestController
@RequestMapping("/lectures/{lectureId}/chapters/{chapterId}/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @Operation(
            summary = "동영상 업로드 요청 (강사)",
            description =
                    "강사가 동영상 업로드를 위한 Video 엔티티를 생성하고 S3 Upload Presigned URL을 받습니다. TEACHER 권한 필요.")
    @PostMapping
    @RequireAuth(roles = {UserRole.TEACHER})
    public VideoUploadResponse uploadVideo(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "강의 ID", example = "1") @PathVariable Long lectureId,
            @Parameter(description = "챕터 ID", example = "1") @PathVariable Long chapterId,
            @Parameter(description = "동영상 업로드 요청 정보") @Valid @RequestBody
                    VideoUploadRequest request) {

        return videoService.createVideoForUpload(userId, lectureId, chapterId, request);
    }

    @Operation(
            summary = "동영상 분할 완료 알림 (Lambda)",
            description = "Lambda에서 동영상 분할 완료 후 호출하여 Video 엔티티의 streamable 상태를 true로 변경합니다.")
    @PostMapping("/{videoId}/finalize")
    @RequireAuth(requireAuth = false) // Lambda에서 호출하므로 인증 불필요
    public ResponseEntity<Void> finalizeVideoUpload(
            @Parameter(description = "강의 ID", example = "1") @PathVariable Long lectureId,
            @Parameter(description = "챕터 ID", example = "1") @PathVariable Long chapterId,
            @Parameter(description = "동영상 ID", example = "1") @PathVariable Long videoId,
            @Parameter(description = "HLS 스트리밍 경로") @RequestParam(required = false)
                    String streamingPath) {

        // streamingPath가 없으면 기본 경로 생성
        if (streamingPath == null) {
            streamingPath =
                    String.format(
                            "lectures/%d/chapters/%d/video/%d/hls", lectureId, chapterId, videoId);
        }

        videoService.finalizeVideoUpload(videoId, streamingPath);
        return ResponseEntity.ok().build();
    }
}
