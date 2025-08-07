package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.LectureEnrollmentRequest;
import com.seniclass.server.domain.student.dto.LectureEnrollmentResponse;
import com.seniclass.server.domain.student.service.LectureEnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Lecture Enrollment", description = "학생 강의 수강 관리 API")
@RestController
@RequestMapping("/students/enrollments")
@RequiredArgsConstructor
public class LectureEnrollmentController {

    private final LectureEnrollmentService lectureEnrollmentService;

    @Operation(summary = "내 수강 강의 목록 조회", description = "현재 로그인한 학생의 수강 중인 강의 목록을 조회합니다.")
    @GetMapping
    @RequireAuth(roles = {UserRole.STUDENT})
    public Page<LectureEnrollmentResponse> getMyEnrollments(
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return lectureEnrollmentService.getCurrentStudentEnrollments(pageable);
    }

    @Operation(summary = "강의 수강 등록", description = "새로운 강의에 수강 등록합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequireAuth(roles = {UserRole.STUDENT})
    public LectureEnrollmentResponse enrollLecture(
            @Valid @RequestBody LectureEnrollmentRequest request) {
        return lectureEnrollmentService.enrollLecture(request);
    }

    @Operation(summary = "수강 취소", description = "수강을 취소합니다.")
    @DeleteMapping("/{enrollmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuth(roles = {UserRole.STUDENT})
    public void cancelEnrollment(
            @Parameter(description = "수강 등록 ID") @PathVariable Long enrollmentId) {
        lectureEnrollmentService.cancelEnrollment(enrollmentId);
    }

    @Operation(summary = "강의로 수강 취소", description = "강의 ID로 수강을 취소합니다.")
    @DeleteMapping("/lecture/{lectureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuth(roles = {UserRole.STUDENT})
    public void cancelEnrollmentByLecture(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        lectureEnrollmentService.cancelEnrollmentByLectureId(lectureId);
    }

    @Operation(summary = "강의 수강 여부 확인", description = "특정 강의에 수강 등록되어 있는지 확인합니다.")
    @GetMapping("/check/{lectureId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Map<String, Boolean> checkEnrollment(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        return Map.of("isEnrolled", lectureEnrollmentService.isEnrolled(lectureId));
    }

    @Operation(summary = "내 수강 강의 수 조회", description = "현재 수강 중인 강의 수를 조회합니다.")
    @GetMapping("/count")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Map<String, Long> getMyEnrollmentCount() {
        return Map.of(
                "enrollmentCount", lectureEnrollmentService.getCurrentStudentEnrollmentCount());
    }

    // 강사/관리자용 API - 강의별 수강생 목록 조회
    @Operation(summary = "특정 강의의 수강생 목록 조회", description = "특정 강의의 수강생 목록을 조회합니다. (강사/관리자 전용)")
    @GetMapping("/lecture/{lectureId}")
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.ADMIN})
    public Page<LectureEnrollmentResponse> getLectureEnrollments(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId,
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return lectureEnrollmentService.getLectureEnrollments(lectureId, pageable);
    }
}
