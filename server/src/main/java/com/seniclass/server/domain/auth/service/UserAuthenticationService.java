package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.student.enums.Gender;
import com.seniclass.server.domain.teacher.domain.Teacher;
import org.springframework.web.multipart.MultipartFile;

public interface UserAuthenticationService {

    AuthenticatedUser authenticate(String email, String password);

    AuthenticatedUser findByIdAndValidate(String userId);

    AuthenticatedUser findByEmailAndValidate(String email);

    String createStudent(
            String name,
            String email,
            Integer age,
            Gender gender,
            String encodedPassword,
            MultipartFile file);

    Teacher createTeacher(
            String name,
            String email,
            Integer age,
            Gender gender,
            String encodedPassword,
            String instruction,
            MultipartFile file);

    boolean existsByEmail(String email);
}
