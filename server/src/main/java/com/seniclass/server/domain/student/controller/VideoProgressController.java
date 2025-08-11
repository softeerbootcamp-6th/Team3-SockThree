package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.VideoProgressRequest;
import com.seniclass.server.domain.student.dto.VideoProgressResponse;
import com.seniclass.server.domain.student.service.VideoProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Video Progress", description = "학생 비디오 진행 상황 관리 API")
@RestController
@RequestMapping("/students/videos")
@RequiredArgsConstructor
public class VideoProgressController {

    private final VideoProgressService videoProgressService;

    @Operation(summary = "비디오 진행 상황 업데이트", description = "학생의 비디오 시청 진행 상황을 업데이트합니다.")
    @PutMapping("/progress")
    @RequireAuth(roles = {UserRole.STUDENT})
    public VideoProgressResponse updateProgress(@Valid @RequestBody VideoProgressRequest request) {
        return videoProgressService.updateProgress(request);
    }

    @Operation(summary = "비디오 진행 상황 삭제", description = "학생의 비디오 진행 상황을 삭제합니다.")
    @DeleteMapping("/progress/{progressId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public void deleteProgress(@Parameter(description = "진행 상황 ID") @PathVariable Long progressId) {
        videoProgressService.deleteProgress(progressId);
    }

    @Operation(summary = "내 비디오 진행 상황 목록 조회", description = "현재 학생의 모든 비디오 진행 상황을 조회합니다.")
    @GetMapping("/progress")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Page<VideoProgressResponse> getCurrentStudentProgress(
            @PageableDefault(size = 20, sort = "updatedDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return videoProgressService.getCurrentStudentProgress(pageable);
    }

    @Operation(summary = "강의별 내 비디오 진행 상황 조회", description = "특정 강의의 비디오 진행 상황을 조회합니다.")
    @GetMapping("/progress/lectures/{lectureId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Page<VideoProgressResponse> getCurrentStudentProgressByLecture(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId,
            @PageableDefault(size = 20, sort = "updatedDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return videoProgressService.getCurrentStudentProgressByLecture(lectureId, pageable);
    }

    @Operation(summary = "특정 비디오 진행 상황 조회", description = "특정 비디오의 현재 학생 진행 상황을 조회합니다.")
    @GetMapping("/{videoId}/progress/my")
    @RequireAuth(roles = {UserRole.STUDENT})
    public VideoProgressResponse getProgressByVideo(
            @Parameter(description = "비디오 ID") @PathVariable Long videoId) {
        return videoProgressService.getProgressByVideo(videoId);
    }

    @Operation(summary = "특정 비디오의 모든 진행 상황 조회", description = "특정 비디오의 모든 학생 진행 상황을 조회합니다. (강사용)")
    @GetMapping("/{videoId}/progress")
    @RequireAuth(roles = {UserRole.TEACHER})
    public Page<VideoProgressResponse> getProgressByVideoForAll(
            @Parameter(description = "비디오 ID") @PathVariable Long videoId,
            @PageableDefault(size = 20, sort = "updatedDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return videoProgressService.getProgressByVideoForAll(videoId, pageable);
    }
}
