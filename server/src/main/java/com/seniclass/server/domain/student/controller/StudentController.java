package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.repository.StudentRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentRepository studentRepository;

  @GetMapping("/profile")
  @RequireAuth(roles = {UserRole.STUDENT})
  public String getMyProfile() {
    // AuthInterceptor에서 이미 학생 역할인지 검증했으므로, 바로 비즈니스 로직 처리
    return "학생 프로필 정보";
  }

  @GetMapping("/my-courses")
  @RequireAuth(roles = {UserRole.STUDENT})
  public String getMyCourses() {
    return "학생의 수강 과목 목록";
  }

  @GetMapping("/all")
  @RequireAuth(roles = {UserRole.ADMIN})
  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  @GetMapping("/public-info")
  public String getPublicInfo() {
    return "공개 정보";
  }

  @GetMapping("/me")
  @RequireAuth
  public Map<String, Object> getCurrentUserInfo() {
    return Map.of(
        "userId", AuthContext.getCurrentUserId(),
        "role", AuthContext.getCurrentUserRole(),
        "isStudent", AuthContext.hasRole(UserRole.STUDENT));
  }
}
