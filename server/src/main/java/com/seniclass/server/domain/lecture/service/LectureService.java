package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.LectureResponse;

public interface LectureService {
    public LectureResponse createLecture(LectureCreateRequest request);
    public LectureResponse getLecture(Long lectureId);
    public LectureResponse updateLecture(Long lectureId, LectureUpdateRequest request);
    public void deleteLecture(Long lectureId);

}
