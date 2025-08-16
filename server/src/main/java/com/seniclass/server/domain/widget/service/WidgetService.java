package com.seniclass.server.domain.widget.service;

import com.seniclass.server.domain.widget.dto.response.AssignmentWidgetResponse;
import com.seniclass.server.domain.widget.dto.response.QnaWidgetResponse;
import com.seniclass.server.domain.widget.dto.response.ReviewWidgetResponse;
import com.seniclass.server.domain.widget.dto.response.TeacherWidgetResponse;

public interface WidgetService {

    /** 과제 위젯 데이터 조회 - 해당 강의의 마감 기한이 가장 빠른 과제 정보 - 해당 강의에서 가장 최근에 제출한 과제의 제출 현황 (피드백 포함) */
    AssignmentWidgetResponse getAssignmentWidget(Long lectureId);

    /** 리뷰 위젯 데이터 조회 - 해당 강의의 평점이 가장 높은 리뷰 3개 - 해당 강의의 수강후기 작성 비율 */
    ReviewWidgetResponse getReviewWidget(Long lectureId);

    /** QNA 위젯 데이터 조회 - 해당 강의의 최근 질문 7개 - 해당 강의에서 내 질문 최대 7개 */
    QnaWidgetResponse getQnaWidget(Long lectureId);

    /** 강사 정보 위젯 데이터 조회 - 강사의 이름, 나이, 성별, 자기소개, 프로필 이미지 */
    TeacherWidgetResponse getTeacherWidget(Long lectureId);
}
