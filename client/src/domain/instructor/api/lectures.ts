import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";

/* endpoint 정의 */
const lecturesApi = createApi("/lectures");

/* Lecture 관련 타입 정의 */
export type LectureResponse = components["schemas"]["LectureResponse"];
export type LectureCreateRequest =
  components["schemas"]["LectureCreateRequest"];
export type LectureUpdateRequest =
  components["schemas"]["LectureUpdateRequest"];
export type UploadTimeCreateRequest =
  components["schemas"]["UploadTimeCreateRequest"];
export type UploadTimeResponse = components["schemas"]["UploadTimeResponse"];
export type WidgetSettingResponse =
  components["schemas"]["WidgetSettingResponse"];
export type WidgetSettingUpdateRequest =
  components["schemas"]["WidgetSettingUpdateRequest"];
export type MyLectureStatusWidgetResponse =
  components["schemas"]["MyLectureStatusWidgetResponse"];

/* Lecture */
// 강의 생성
const createLecture = (data: {
  request: LectureCreateRequest;
  file?: File;
}) => {
  const formData = new FormData();
  formData.append("request", JSON.stringify(data.request));

  if (data.file) {
    formData.append("file", data.file);
  }

  return lecturesApi.post<LectureResponse, FormData>("", formData);
};

// 강의 수정
const updateLecture = (
  lectureId: number,
  data: { request: LectureUpdateRequest; file?: File }
) => {
  const formData = new FormData();
  formData.append("request", JSON.stringify(data.request));

  if (data.file) {
    formData.append("file", data.file);
  }

  return lecturesApi.put<LectureResponse, FormData>(`/${lectureId}`, formData);
};

// 강의 삭제
const deleteLecture = (lectureId: number) =>
  lecturesApi.del<void>(`/${lectureId}`);

// 나의 강의 현황 사이드바 위젯
const getMyLectureStatusWidget = (lectureId: number) =>
  lecturesApi.get<MyLectureStatusWidgetResponse>(
    `/${lectureId}/side-widget/my-lecture-status`
  );

/* Widget Setting */
// 위젯 설정 수정
const updateWidgetSettings = (
  lectureId: number,
  data: WidgetSettingUpdateRequest
) =>
  lecturesApi.put<WidgetSettingResponse[], WidgetSettingUpdateRequest>(
    `/${lectureId}/widget-settings`,
    data
  );
