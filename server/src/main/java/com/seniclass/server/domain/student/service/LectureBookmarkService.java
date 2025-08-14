package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.student.dto.request.LectureBookmarkRequest;
import com.seniclass.server.domain.student.dto.response.LectureBookmarkResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureBookmarkService {

    /** 현재 로그인한 학생의 북마크한 강의 목록 조회 */
    Page<LectureBookmarkResponse> getCurrentStudentBookmarks(Pageable pageable);

    /** 강의 북마크 추가 */
    LectureBookmarkResponse addBookmark(LectureBookmarkRequest request);

    /** 강의 북마크 삭제 */
    void removeBookmark(Long bookmarkId);

    /** 강의 ID로 북마크 삭제 */
    void removeBookmarkByLectureId(Long lectureId);

    /** 강의 북마크 여부 확인 */
    boolean isBookmarked(Long lectureId);

    /** 특정 학생의 북마크 목록 조회 (관리자용) */
    Page<LectureBookmarkResponse> getStudentBookmarks(Long studentId, Pageable pageable);
}
