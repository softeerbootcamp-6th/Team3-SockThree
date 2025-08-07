package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.StudentInfoResponse;
import com.seniclass.server.domain.student.dto.StudentUpdateRequest;
import com.seniclass.server.domain.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student", description = "학생 관리 API")
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // === 학생 본인 관리 API ===

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 학생의 정보를 조회합니다.")
    @GetMapping("/my-info")
    @RequireAuth(roles = {UserRole.STUDENT})
    public StudentInfoResponse getMyInfo() {
        return studentService.getCurrentStudentInfo();
    }

    @Operation(summary = "내 정보 수정", description = "현재 로그인한 학생의 정보를 수정합니다.")
    @PutMapping("/my-info")
    @RequireAuth(roles = {UserRole.STUDENT})
    public StudentInfoResponse updateMyInfo(@Valid @RequestBody StudentUpdateRequest request) {
        return studentService.updateCurrentStudentInfo(request);
    }
}
