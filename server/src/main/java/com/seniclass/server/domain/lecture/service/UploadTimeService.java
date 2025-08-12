package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.domain.UploadTime;
import com.seniclass.server.domain.lecture.dto.request.UploadTimeCreateRequest;
import com.seniclass.server.domain.lecture.exception.errorcode.UploadTimeErrorCode;
import com.seniclass.server.domain.lecture.repository.UploadTimeRepository;
import com.seniclass.server.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<UploadTime> getAllUploadTimesEntity(Lecture lecture) {
        List<UploadTime> uplodaTimeList = uploadTimeRepository.findAllByLectureId(lecture.getId());
        if (uplodaTimeList.isEmpty()) {
            throw new CommonException(UploadTimeErrorCode.UPLOAD_TIME_NOT_FOUND);
        }
        return uplodaTimeList;
    }
}
