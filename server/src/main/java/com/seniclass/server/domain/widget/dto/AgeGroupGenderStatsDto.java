package com.seniclass.server.domain.widget.dto;

import com.seniclass.server.domain.student.enums.Gender;

/** 연령대별 성별 통계 조회 결과 */
public record AgeGroupGenderStatsDto(String ageGroup, Gender gender, Long count) {}
