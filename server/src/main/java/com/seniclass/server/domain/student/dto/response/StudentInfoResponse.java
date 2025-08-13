package com.seniclass.server.domain.student.dto.response;

import com.seniclass.server.domain.student.enums.Gender;

public record StudentInfoResponse(Long id, String name, String email, Integer age, Gender gender) {}
