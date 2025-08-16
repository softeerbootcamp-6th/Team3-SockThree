import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";

/* Endpoint 정의 */
const bookmarksApi = createApi("/bookmarks");

/* Bookmark 관련 타입 정의 */
export type LectureBookmarkRequest =
  components["schemas"]["LectureBookmarkRequest"];
export type LectureBookmarkResponse =
  components["schemas"]["LectureBookmarkResponse"];
export type PageLectureBookmarkResponse =
  components["schemas"]["PageLectureBookmarkResponse"];
export type Pageable = components["schemas"]["Pageable"];

/* Lecture Bookmark */
// 내 북마크 목록 조회
const getMyBookmarks = (pageable: Pageable) =>
  bookmarksApi.get<PageLectureBookmarkResponse>("", { pageable });

// 북마크 추가
const addBookmark = (data: LectureBookmarkRequest) =>
  bookmarksApi.post<LectureBookmarkResponse, LectureBookmarkRequest>("", data);

// 북마크 여부 확인
const checkBookmark = (lectureId: number) =>
  bookmarksApi.get<{ [key: string]: boolean }>(`/check/${lectureId}`);

// 북마크 삭제
const removeBookmarkByBookmarkId = (bookmarkId: number) =>
  bookmarksApi.del<void>(`/${bookmarkId}`);

const removeBookmarkByLectureId = (lectureId: number) =>
  bookmarksApi.del<void>(`/lecture/${lectureId}`);
