import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";

/* endpoint 정의 */
const lectureApi = createApi("/lectures");

/* Lecture 관련 타입 정의 */
export type LectureResponse = components["schemas"]["LectureResponse"];
export type TeacherWidgetResponse =
  components["schemas"]["TeacherWidgetResponse"];
export type LectureStatisticsResponse =
  components["schemas"]["LectureStatisticsResponse"];
export type ReviewWidgetResponse =
  components["schemas"]["ReviewWidgetResponse"];
export type QnaWidgetResponse = components["schemas"]["QnaWidgetResponse"];
export type AssignmentWidgetResponse =
  components["schemas"]["AssignmentWidgetResponse"];

export type LectureBannerResponse =
  components["schemas"]["LectureBannerResponse"];

/* Lecture */
// 강의 단일 조회 (강사, 수강생)
const getLecture = (lectureId: number) =>
  lectureApi.get<LectureResponse>(`/${lectureId}`);

// 강좌 배너 정보
const getLectureBanner = (lectureId: number) =>
  lectureApi.get<LectureBannerResponse>(`/${lectureId}/banner`);

/* Widget  */
// 강사 정보 위젯 데이터 조회 (수강생, 강사)
const getTeacherWidget = (lectureId: number) =>
  lectureApi.get<TeacherWidgetResponse>(`/${lectureId}/widgets/teacher`);

// 강좌 수강생 통계 조회 (강사, 수강생)
const getLectureStatistics = (lectureId: number) =>
  lectureApi.get<LectureStatisticsResponse>(`/${lectureId}/widgets/statistics`);

// 리뷰 위젯 데이터 조회 (수강생, 강사)
const getReviewWidget = (lectureId: number) =>
  lectureApi.get<ReviewWidgetResponse>(`/${lectureId}/widgets/review`);

// QNA 위젯 데이터 조회 (수강생, 강사)
const getQnaWidget = (lectureId: number) =>
  lectureApi.get<QnaWidgetResponse>(`/${lectureId}/widgets/qna`);

// 과제 위젯 데이터 조회 (수강생, 강사)
const getAssignmentWidget = (lectureId: number) =>
  lectureApi.get<AssignmentWidgetResponse>(`/${lectureId}/widgets/assignment`);
