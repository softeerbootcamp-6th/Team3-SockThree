package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.LectureBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureBookmarkRepository extends JpaRepository<LectureBookmark, Long> {}
