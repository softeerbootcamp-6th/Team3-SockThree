package com.seniclass.server.domain.widget.dto;

/** 학생별 비디오 수강 개수 조회 결과 */
public record StudentVideoCountDto(Long studentId, Long videoCount) {}
