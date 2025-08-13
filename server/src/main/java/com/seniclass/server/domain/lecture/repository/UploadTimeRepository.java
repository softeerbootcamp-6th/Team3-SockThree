package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.UploadTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadTimeRepository extends JpaRepository<UploadTime, Long> {

    List<UploadTime> findAllByLectureId(Long lectureId);
}
