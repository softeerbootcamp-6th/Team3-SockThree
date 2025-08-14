package com.seniclass.server.domain.widget.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.widget.dto.response.LectureStatisticsResponse;
import com.seniclass.server.domain.widget.service.LectureStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Lecture Statistics", description = "강좌 통계 관리 API")
@RestController
@RequestMapping("/lectures/{lectureId}/statistics")
@RequiredArgsConstructor
public class LectureStatisticsController {

    private final LectureStatisticsService lectureStatisticsService;

    @Operation(
            summary = "강좌 수강생 통계 조회 (강사, 수강생)",
            description =
                    """
            강좌에 속한 수강생들의 상세 통계를 조회합니다.

            포함되는 통계:
            1. 평균 시청 비디오 수
            2. 모든 비디오를 완주한 학생 수
            3. 전체 수강생에 대한 최근 5개의 과제 제출률
            4. 50대 이상 5살마다 남녀로 나뉜 수강 연령대 통계
            """)
    @GetMapping
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.STUDENT})
    public LectureStatisticsResponse getLectureStatistics(
            @Parameter(description = "강좌 ID") @PathVariable Long lectureId) {
        return lectureStatisticsService.getLectureStatistics(lectureId);
    }
}
