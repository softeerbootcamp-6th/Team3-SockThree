package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.student.enums.Gender;

public interface UserAuthenticationService {

    AuthenticatedUser authenticate(String email, String password);

    AuthenticatedUser findByIdAndValidate(String userId);

    AuthenticatedUser findByEmailAndValidate(String email);

    String createStudent(
            String name, String email, Integer age, Gender gender, String encodedPassword);

    boolean existsByEmail(String email);
}
