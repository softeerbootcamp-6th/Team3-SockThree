import { createApi } from "@/shared/api/core/createApi";
import type { components } from "@/shared/types/openapi";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

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

// ========================
//   * bookmarks API 훅 *
// ========================

// 내 북마크 목록 조회
export const useMyBookmarks = (pageable: Pageable) => {
  return useQuery({
    queryKey: ["bookmarks", "my", pageable],
    queryFn: () => getMyBookmarks(pageable),
  });
};

// 북마크 추가
export const useAddBookmark = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: addBookmark,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ["bookmarks"] });
      await queryClient.invalidateQueries({ queryKey: ["bookmark-check"] });
    },
  });
};

// 북마크 여부 확인
export const useCheckBookmark = (lectureId: number) => {
  return useQuery({
    queryKey: ["bookmark-check", lectureId],
    queryFn: () => checkBookmark(lectureId),
    enabled: !!lectureId,
  });
};

// 북마크 삭제
/**
 * 사용법
 * useRemoveBookmark()({ bookmarkId: 1 });
 * useRemoveBookmark()({ lectureId: 1 });
 * bookmarkId 또는 lectureId 중 하나는 필수입니다. 명시적으로 작성해주세요
 */
export const useRemoveBookmark = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      bookmarkId,
      lectureId,
    }: {
      bookmarkId?: number;
      lectureId?: number;
    }) => {
      if (bookmarkId) {
        return removeBookmarkByBookmarkId(bookmarkId);
      } else if (lectureId) {
        return removeBookmarkByLectureId(lectureId);
      } else {
        throw new Error("bookmarkId 또는 lectureId 중 하나는 필수입니다.");
      }
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ["bookmarks"] });
    },
  });
};
