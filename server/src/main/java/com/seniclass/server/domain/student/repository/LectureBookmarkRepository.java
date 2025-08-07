package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.LectureBookmark;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureBookmarkRepository extends JpaRepository<LectureBookmark, Long> {

    /** 학생 ID로 북마크 목록 조회 (생성일시 내림차순, 페이징) */
    Page<LectureBookmark> findByStudentIdOrderByCreatedDtDesc(Long studentId, Pageable pageable);

    /** 학생 ID와 강의 ID로 북마크 조회 */
    Optional<LectureBookmark> findByStudentIdAndLectureId(Long studentId, Long lectureId);

    /** 학생 ID와 강의 ID로 북마크 존재 여부 확인 */
    boolean existsByStudentIdAndLectureId(Long studentId, Long lectureId);

    /** 학생 ID로 모든 북마크 삭제 */
    void deleteByStudentId(Long studentId);

    /** 강의 ID로 모든 북마크 삭제 */
    void deleteByLectureId(Long lectureId);

    /** 학생 ID로 북마크 개수 조회 */
    long countByStudentId(Long studentId);
}
