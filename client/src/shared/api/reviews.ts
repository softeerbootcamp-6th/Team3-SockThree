import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";
import type { Pageable, ReviewResponse } from "@/domain/student/api/reviews";
import { useQuery } from "@tanstack/react-query";

/* endpoint 정의 */
const reviewsApi = createApi("/reviews");

/* Review 관련 타입 정의 */
export type PageReviewResponse = components["schemas"]["PageReviewResponse"];

/* Review */
// 리뷰 조회
const getReview = (reviewId: number) =>
  reviewsApi.get<ReviewResponse>(`/${reviewId}`);

// 강의별 리뷰 목록 조회
const getReviewsByLecture = (lectureId: number, pageable: Pageable) =>
  reviewsApi.get<PageReviewResponse>(`/${lectureId}/reviews`, { pageable });

// ========================
//   * Review API 훅 *
// ========================

// 리뷰 조회
export const useReview = (reviewId: number) => {
  return useQuery({
    queryKey: ["reviews", reviewId],
    queryFn: () => getReview(reviewId),
    enabled: !!reviewId,
  });
};

// 강의별 리뷰 목록 조회
export const useReviewsByLecture = (lectureId: number, pageable: Pageable) => {
  return useQuery({
    queryKey: ["reviews", "lecture", lectureId, pageable],
    queryFn: () => getReviewsByLecture(lectureId, pageable),
    enabled: !!lectureId,
  });
};
