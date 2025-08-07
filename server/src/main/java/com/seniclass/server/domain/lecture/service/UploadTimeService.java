package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.domain.UploadTime;
import com.seniclass.server.domain.lecture.dto.request.UploadTimeCreateRequest;
import com.seniclass.server.domain.lecture.repository.UploadTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadTimeService {
    private final UploadTimeRepository uploadTimeRepository;

    @Transactional
    public void createUploadTime(UploadTimeCreateRequest request, Lecture lecture) {
        UploadTime uploadTime =
                UploadTime.create(request.dayOfWeek(), request.scheduledAt(), lecture);
        uploadTimeRepository.save(uploadTime);
    }
}
