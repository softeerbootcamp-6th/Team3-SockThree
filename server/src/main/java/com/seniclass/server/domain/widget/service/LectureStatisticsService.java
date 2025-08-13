package com.seniclass.server.domain.widget.service;

import com.seniclass.server.domain.widget.dto.response.LectureStatisticsResponse;

public interface LectureStatisticsService {

    /**
     * 강좌에 속한 수강생들의 통계를 조회합니다.
     *
     * @param lectureId 강좌 ID
     * @return 강좌 통계 정보
     */
    LectureStatisticsResponse getLectureStatistics(Long lectureId);
}
