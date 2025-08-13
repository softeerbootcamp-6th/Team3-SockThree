package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.LectureInfoWidgetResponse;
import com.seniclass.server.domain.lecture.dto.response.LectureResponse;
import com.seniclass.server.domain.lecture.dto.response.MyLectureStatusWidgetResponse;
import org.springframework.web.multipart.MultipartFile;

public interface LectureService {
    public LectureResponse createLecture(
            Long UserId, LectureCreateRequest request, MultipartFile file);

    public LectureResponse getLecture(Long lectureId);

    public LectureResponse updateLecture(
            Long userId, Long lectureId, LectureUpdateRequest request, MultipartFile file);

    public void deleteLecture(Long lectureId);

    public LectureInfoWidgetResponse getLectureInfoWidget(Long lectureId);

    public MyLectureStatusWidgetResponse getMyLectureStatusWidget(Long userId, Long lectureId);
}
