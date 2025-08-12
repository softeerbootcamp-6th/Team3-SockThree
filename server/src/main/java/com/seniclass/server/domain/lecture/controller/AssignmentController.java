package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.AssignmentCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.AssignmentUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.AssignmentResponse;
import com.seniclass.server.domain.lecture.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Assignment", description = "과제 관리 API")
@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Operation(summary = "과제 생성 (강사)", description = "강사가 새로운 과제를 생성합니다. TEACHER 권한 필요.")
    @PostMapping
    @RequireAuth(roles = {UserRole.TEACHER})
    public AssignmentResponse createAssignment(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(hidden = true) @Valid @RequestBody AssignmentCreateRequest request) {
        return assignmentService.createAssignment(userId, request);
    }

    @Operation(summary = "과제 단일 조회 (강사, 수강생)", description = "과제 ID로 특정 과제의 상세 정보를 조회합니다.")
    @GetMapping("/{assignmentId}")
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.STUDENT})
    public AssignmentResponse getAssignment(
            @Parameter(description = "조회할 과제 ID") @PathVariable Long assignmentId) {
        return assignmentService.getAssignment(assignmentId);
    }

    @Operation(summary = "과제 수정 (강사)", description = "강사가 본인의 과제 정보를 수정합니다. TEACHER 권한 필요.")
    @PutMapping("/{assignmentId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public AssignmentResponse updateAssignment(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "수정할 과제 ID") @PathVariable Long assignmentId,
            @Parameter(hidden = true) @Valid @RequestBody AssignmentUpdateRequest request) {
        return assignmentService.updateAssignment(userId, assignmentId, request);
    }

    @Operation(summary = "과제 삭제 (강사)", description = "강사가 본인의 과제를 삭제합니다. TEACHER 권한 필요.")
    @DeleteMapping("/{assignmentId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public ResponseEntity<Void> deleteAssignment(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "삭제할 과제 ID") @PathVariable Long assignmentId) {
        assignmentService.deleteAssignment(userId, assignmentId);
        return ResponseEntity.ok().build();
    }
}
