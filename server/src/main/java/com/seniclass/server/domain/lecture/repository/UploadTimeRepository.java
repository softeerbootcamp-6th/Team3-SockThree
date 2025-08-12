package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.UploadTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadTimeRepository extends JpaRepository<UploadTime, Long> {

    List<UploadTime> findAllByLectureId(Long lectureId);
}
