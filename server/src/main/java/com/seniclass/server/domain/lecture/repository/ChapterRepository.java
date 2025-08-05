package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {}
