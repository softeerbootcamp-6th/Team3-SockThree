import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";
import type { Pageable, ReviewResponse } from "@/domain/student/api/reviews";

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
