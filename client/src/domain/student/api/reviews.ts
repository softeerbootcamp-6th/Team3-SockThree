import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

/* Endpoint 정의 */
const reviewsApi = createApi("/reviews");

/* Review 관련 타입 정의 */
export type ReviewCreateRequest = components["schemas"]["ReviewCreateRequest"];
export type ReviewUpdateRequest = components["schemas"]["ReviewUpdateRequest"];
export type ReviewResponse = components["schemas"]["ReviewResponse"];
export type Pageable = components["schemas"]["Pageable"];

/* Review */
// 리뷰 작성
const createReview = (data: ReviewCreateRequest) =>
  reviewsApi.post<ReviewResponse, ReviewCreateRequest>("", data);

// 리뷰 수정
const updateReview = (reviewId: number, data: ReviewUpdateRequest) =>
  reviewsApi.put<ReviewResponse, ReviewUpdateRequest>(`/${reviewId}`, data);

// 리뷰 삭제
const deleteReview = (reviewId: number) => reviewsApi.del<void>(`/${reviewId}`);

// 내가 작성한 리뷰 조회
const getMyReviewByLecture = (lectureId: number) =>
  reviewsApi.get<ReviewResponse>(`lectures/${lectureId}/my`);

// ========================
//   * Review API 훅 *
// ========================

// 리뷰 작성
export const useCreateReview = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createReview,
    onSuccess: async (_, variables) => {
      await queryClient.invalidateQueries({
        queryKey: ["reviews", "lecture", variables.lectureId],
      });
      await queryClient.invalidateQueries({
        queryKey: ["my-review", "lecture", variables.lectureId],
      });
    },
  });
};

// 리뷰 수정
export const useUpdateReview = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      reviewId,
      data,
    }: {
      reviewId: number;
      data: ReviewUpdateRequest;
    }) => updateReview(reviewId, data),
    onSuccess: async (data, variables) => {
      await queryClient.invalidateQueries({
        queryKey: ["reviews", variables.reviewId],
      });
      await queryClient.invalidateQueries({
        queryKey: ["reviews", "lecture", data.lectureId],
      });
      await queryClient.invalidateQueries({
        queryKey: ["my-review", "lecture", data.lectureId],
      });
    },
  });
};

// 리뷰 삭제
export const useDeleteReview = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteReview,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ["reviews"] });
      await queryClient.invalidateQueries({ queryKey: ["my-review"] });
    },
  });
};

// 내가 작성한 리뷰 조회
export const useMyReviewByLecture = (lectureId: number) => {
  return useQuery({
    queryKey: ["my-review", "lecture", lectureId],
    queryFn: () => getMyReviewByLecture(lectureId),
    enabled: !!lectureId,
  });
};
