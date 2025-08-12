package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.ChapterCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.ChapterUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.ChapterResponse;
import com.seniclass.server.domain.lecture.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chapter", description = "챕터 관리 API")
@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @Operation(summary = "챕터 생성 (강사)", description = "강사가 새로운 챕터를 생성합니다. TEACHER 권한 필요.")
    @PostMapping
    @RequireAuth(roles = {UserRole.TEACHER})
    public ChapterResponse createChapter(
            @Valid @RequestBody ChapterCreateRequest request,
            @RequestAttribute("userId") Long userId) {
        return chapterService.createChapter(userId, request);
    }

    @Operation(summary = "챕터 단일 조회 (강사, 수강생)", description = "챕터 ID로 특정 챕터의 정보를 조회합니다.")
    @GetMapping("/{chapterId}")
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.STUDENT})
    public ChapterResponse getChapter(
            @Parameter(description = "조회할 챕터 ID") @PathVariable Long chapterId) {
        return chapterService.getChapter(chapterId);
    }

    @Operation(summary = "챕터 수정 (강사)", description = "강사가 챕터 정보를 수정합니다. TEACHER 권한 필요.")
    @PatchMapping("/{chapterId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public ChapterResponse updateChapter(
            @Parameter(description = "수정할 챕터 ID") @PathVariable Long chapterId,
            @Valid @RequestBody ChapterUpdateRequest request,
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId) {
        return chapterService.updateChapter(userId, chapterId, request);
    }

    @Operation(summary = "챕터 삭제 (강사)", description = "강사가 챕터를 삭제합니다. TEACHER 권한 필요.")
    @DeleteMapping("/{chapterId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public ResponseEntity<Void> deleteChapter(
            @Parameter(description = "삭제할 챕터 ID") @PathVariable Long chapterId,
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId) {
        chapterService.deleteChapter(userId, chapterId);
        return ResponseEntity.ok().build();
    }
}
