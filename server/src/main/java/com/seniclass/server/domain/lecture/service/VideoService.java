package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.dto.request.VideoUploadRequest;
import com.seniclass.server.domain.lecture.dto.response.VideoUploadResponse;

public interface VideoService {

    VideoUploadResponse createVideoForUpload(
            Long userId, Long lectureId, Long chapterId, VideoUploadRequest request);

    void finalizeVideoUpload(Long lectureId, Long chapterId, Long videoId, String streamingPath);
}
