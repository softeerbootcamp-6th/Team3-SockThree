package com.seniclass.server.domain.student.dto;

import com.seniclass.server.domain.student.enums.Gender;

public record StudentInfoRequest(
        String name, String email, Integer age, Gender gender, String password) {}
