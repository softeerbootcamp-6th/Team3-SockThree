import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

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

// ========================
//   * lectures API 훅  *
// ========================

// 강의 생성 (Mutation)
export const useCreateLecture = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createLecture,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ["lectures"] });
    },
  });
};

// 강의 수정 (Mutation)
export const useUpdateLecture = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      lectureId,
      data,
    }: {
      lectureId: number;
      data: { request: LectureUpdateRequest; file?: File };
    }) => updateLecture(lectureId, data),
    onSuccess: async (_, variables) => {
      await queryClient.invalidateQueries({ queryKey: ["lectures"] });
      await queryClient.invalidateQueries({
        queryKey: ["lecture", variables.lectureId],
      });
    },
  });
};

// 강의 삭제 (Mutation)
export const useDeleteLecture = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteLecture,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ["lectures"] });
    },
  });
};

// 나의 강의 현황 위젯 조회 (Query)
export const useMyLectureStatusWidget = (lectureId: number) => {
  return useQuery({
    queryKey: ["lecture", lectureId, "my-status-widget"],
    queryFn: () => getMyLectureStatusWidget(lectureId),
    enabled: !!lectureId,
  });
};

// 위젯 설정 수정 (Mutation)
export const useUpdateWidgetSettings = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      lectureId,
      data,
    }: {
      lectureId: number;
      data: WidgetSettingUpdateRequest;
    }) => updateWidgetSettings(lectureId, data),
    onSuccess: async (_, variables) => {
      await queryClient.invalidateQueries({
        queryKey: ["lecture", variables.lectureId, "widget-settings"],
      });
    },
  });
};
