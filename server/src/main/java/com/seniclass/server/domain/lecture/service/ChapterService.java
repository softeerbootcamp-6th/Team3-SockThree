package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.dto.request.ChapterCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.ChapterUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.ChapterResponse;

public interface ChapterService {
    ChapterResponse createChapter(Long userId, ChapterCreateRequest request);

    ChapterResponse getChapter(Long chapterId);

    ChapterResponse updateChapter(Long userId, Long chapterId, ChapterUpdateRequest request);

    void deleteChapter(Long userId, Long chapterId);
}
