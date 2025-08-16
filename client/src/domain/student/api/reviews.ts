import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";

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
