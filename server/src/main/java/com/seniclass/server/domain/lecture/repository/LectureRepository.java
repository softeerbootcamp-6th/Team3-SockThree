package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {}
