package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.LectureResponse;
import com.seniclass.server.domain.lecture.service.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Lecture", description = "강의 관리 API")
@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @Operation(summary = "강의 생성", description = "강사가 새로운 강의를 생성합니다. TEACHER 권한 필요.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequireAuth(roles = {UserRole.TEACHER})
    public LectureResponse createLecture(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "강좌 생성에 필요한 정보") @Valid @RequestPart("request") LectureCreateRequest request,
            @Parameter(description = "강좌 이미지 파일") @RequestPart("file") MultipartFile file) {
        return lectureService.createLecture(userId, request, file);
    }

    @Operation(summary = "강의 단일 조회", description = "강의 ID로 특정 강의의 상세 정보를 조회합니다.")
    @GetMapping("/{lectureId}")
    public LectureResponse getLecture(
            @Parameter(description = "조회할 강의 ID") @PathVariable Long lectureId) {
        return lectureService.getLecture(lectureId);
    }

    @Operation(summary = "강의 수정", description = "강사가 본인의 강의 정보를 수정합니다. TEACHER 권한 필요.")
    @PutMapping(value = "/{lectureId}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequireAuth(roles = {UserRole.TEACHER})
    public LectureResponse updateLecture(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "수정할 강의 ID") @PathVariable Long lectureId,
            @Parameter(description = "수정할 강의 정보") @Valid @RequestPart(value = "request") LectureUpdateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return lectureService.updateLecture(userId, lectureId, request, file);
    }

    @Operation(summary = "강의 삭제", description = "강사가 본인의 강의를 삭제합니다. TEACHER 권한 필요.")
    @DeleteMapping("/{lectureId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public ResponseEntity<Void> deleteLecture(
            @Parameter(description = "삭제할 강의 ID") @PathVariable Long lectureId) {
        lectureService.deleteLecture(lectureId);
        return ResponseEntity.ok().build();
    }
}
