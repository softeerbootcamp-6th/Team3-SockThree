package com.seniclass.server.domain.widget.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.widget.dto.response.*;
import com.seniclass.server.domain.widget.service.LectureStatisticsService;
import com.seniclass.server.domain.widget.service.WidgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Widget", description = "위젯 데이터 조회 API (추후 하나의 API로 통합 예정)")
@RestController
@RequestMapping("/lectures/{lectureId}/widgets")
@RequiredArgsConstructor
public class WidgetController {

    private final LectureStatisticsService lectureStatisticsService;
    private final WidgetService widgetService;

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
    @GetMapping("/statistics")
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.STUDENT})
    public LectureStatisticsResponse getLectureStatistics(
            @Parameter(description = "강좌 ID") @PathVariable Long lectureId) {
        return lectureStatisticsService.getLectureStatistics(lectureId);
    }

    @Operation(
            summary = "과제 위젯 데이터 조회 (수강생)",
            description =
                    """
            특정 강의의 과제 위젯에 필요한 데이터를 조회합니다.

            포함되는 정보:
            1. 해당 강의의 마감 기한이 가장 빠른 과제 정보
            2. 해당 강의에서 가장 최근에 제출한 과제의 제출 현황 (강사의 피드백 포함)
            """)
    @GetMapping("/assignment")
    @RequireAuth(roles = {UserRole.STUDENT, UserRole.TEACHER})
    public AssignmentWidgetResponse getAssignmentWidget(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        return widgetService.getAssignmentWidget(lectureId);
    }

    @Operation(
            summary = "리뷰 위젯 데이터 조회 (수강생, 강사)",
            description =
                    """
            특정 강의의 리뷰 위젯에 필요한 데이터를 조회합니다.

            포함되는 정보:
            1. 해당 강의의 평점이 가장 높은 리뷰 3개
            2. 해당 강의의 전체 수강생 대비 수강후기 작성 비율
            """)
    @GetMapping("/review")
    @RequireAuth(roles = {UserRole.STUDENT, UserRole.TEACHER})
    public ReviewWidgetResponse getReviewWidget(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        return widgetService.getReviewWidget(lectureId);
    }

    @Operation(
            summary = "QNA 위젯 데이터 조회 (수강생, 강사)",
            description =
                    """
            특정 강의의 QNA 위젯에 필요한 데이터를 조회합니다.

            포함되는 정보:
            1. 해당 강의의 최근 질문 7개
            2. 해당 강의에서 내가 한 질문 최대 7개
            """)
    @GetMapping("/qna")
    @RequireAuth(roles = {UserRole.STUDENT, UserRole.TEACHER})
    public QnaWidgetResponse getQnaWidget(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        return widgetService.getQnaWidget(lectureId);
    }

    @Operation(
            summary = "강사 정보 위젯 데이터 조회 (수강생, 강사)",
            description =
                    """
            특정 강의의 강사 정보 위젯에 필요한 데이터를 조회합니다.

            포함되는 정보:
            1. 강사의 이름, 나이, 성별, 자기소개, 프로필 이미지
            """)
    @GetMapping("/teacher")
    public TeacherWidgetResponse getTeacherWidget(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        return widgetService.getTeacherWidget(lectureId);
    }
}
