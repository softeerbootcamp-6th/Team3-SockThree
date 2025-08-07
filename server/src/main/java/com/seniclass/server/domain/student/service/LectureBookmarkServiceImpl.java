package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.student.domain.LectureBookmark;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.LectureBookmarkRequest;
import com.seniclass.server.domain.student.dto.LectureBookmarkResponse;
import com.seniclass.server.domain.student.exception.errorcode.LectureBookmarkErrorCode;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.LectureBookmarkRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LectureBookmarkServiceImpl implements LectureBookmarkService {

    private final LectureBookmarkRepository lectureBookmarkRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    /** 현재 로그인한 학생의 북마크한 강의 목록 조회 */
    @Override
    public Page<LectureBookmarkResponse> getCurrentStudentBookmarks(Pageable pageable) {
        Long studentId = AuthContext.getCurrentUserId();

        Page<LectureBookmark> bookmarks =
                lectureBookmarkRepository.findByStudentIdOrderByCreatedDtDesc(studentId, pageable);

        return bookmarks.map(this::toResponse);
    }

    /** 강의 북마크 추가 */
    @Override
    @Transactional
    public LectureBookmarkResponse addBookmark(LectureBookmarkRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        // 이미 북마크한 강의인지 확인
        if (lectureBookmarkRepository.existsByStudentIdAndLectureId(
                studentId, request.lectureId())) {
            throw new CommonException(LectureBookmarkErrorCode.LECTURE_BOOKMARK_ALREADY_EXISTS);
        }

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        Lecture lecture =
                lectureRepository
                        .findById(request.lectureId())
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                StudentErrorCode
                                                        .INTERNAL_ERROR)); // 추후 LectureErrorCode로
        // 변경 필요

        LectureBookmark bookmark = LectureBookmark.createLectureBookmark(student, lecture);
        LectureBookmark saved = lectureBookmarkRepository.save(bookmark);

        log.info("Lecture bookmark added: student={}, lecture={}", studentId, request.lectureId());
        return toResponse(saved);
    }

    /** 강의 북마크 삭제 */
    @Override
    @Transactional
    public void removeBookmark(Long bookmarkId) {
        Long studentId = AuthContext.getCurrentUserId();

        LectureBookmark bookmark =
                lectureBookmarkRepository
                        .findById(bookmarkId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        // 현재 로그인한 학생의 북마크인지 확인
        if (!bookmark.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        lectureBookmarkRepository.delete(bookmark);
        log.info("Lecture bookmark removed: id={}, student={}", bookmarkId, studentId);
    }

    /** 강의 ID로 북마크 삭제 */
    @Override
    @Transactional
    public void removeBookmarkByLectureId(Long lectureId) {
        Long studentId = AuthContext.getCurrentUserId();

        LectureBookmark bookmark =
                lectureBookmarkRepository
                        .findByStudentIdAndLectureId(studentId, lectureId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureBookmarkErrorCode
                                                        .LECTURE_BOOKMARK_NOT_FOUND));

        lectureBookmarkRepository.delete(bookmark);
        log.info(
                "Lecture bookmark removed by lecture: lecture={}, student={}",
                lectureId,
                studentId);
    }

    /** 강의 북마크 여부 확인 */
    @Override
    public boolean isBookmarked(Long lectureId) {
        Long studentId = AuthContext.getCurrentUserId();

        return lectureBookmarkRepository.existsByStudentIdAndLectureId(studentId, lectureId);
    }

    /** 특정 학생의 북마크 목록 조회 (관리자용) */
    @Override
    public Page<LectureBookmarkResponse> getStudentBookmarks(Long studentId, Pageable pageable) {
        Page<LectureBookmark> bookmarks =
                lectureBookmarkRepository.findByStudentIdOrderByCreatedDtDesc(studentId, pageable);

        return bookmarks.map(this::toResponse);
    }

    private LectureBookmarkResponse toResponse(LectureBookmark bookmark) {
        return new LectureBookmarkResponse(
                bookmark.getId(),
                bookmark.getStudent().getId(),
                bookmark.getLecture().getId(),
                bookmark.getLecture().getName(),
                bookmark.getLecture().getSubCategory().getName(),
                bookmark.getLecture().getLevel().name(),
                bookmark.getLecture().getFee(),
                bookmark.getCreatedDt());
    }
}
