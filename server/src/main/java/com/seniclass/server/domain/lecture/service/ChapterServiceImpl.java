package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Chapter;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.dto.request.ChapterCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.ChapterUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.ChapterResponse;
import com.seniclass.server.domain.lecture.exception.errorcode.ChapterErrorCode;
import com.seniclass.server.domain.lecture.exception.errorcode.LectureErrorCode;
import com.seniclass.server.domain.lecture.repository.ChapterRepository;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.user.exception.errorcode.UserErrorCode;
import com.seniclass.server.global.exception.CommonException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final LectureRepository lectureRepository;

    @Override
    public ChapterResponse createChapter(Long userId, ChapterCreateRequest request) {
        Lecture lecture =
                lectureRepository
                        .findById(request.lectureId())
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));
        Chapter chapter = Chapter.createChapter(request.name(), lecture);
        // Validate that the user is the teacher of the lecture
        // TODO lectureService 의 validateLectureTeacher 메서드로 분리
        if (!Objects.equals(lecture.getTeacher().getId(), userId)) {
            throw new CommonException(UserErrorCode.USER_NOT_AUTHORIZED);
        }
        Chapter savedChapter = chapterRepository.save(chapter);
        return ChapterResponse.from(savedChapter);
    }

    @Override
    @Transactional(readOnly = true)
    public ChapterResponse getChapter(Long chapterId) {
        Chapter chapter = getChapterEntity(chapterId);
        return ChapterResponse.from(chapter);
    }

    @Override
    public ChapterResponse updateChapter(
            Long userId, Long chapterId, ChapterUpdateRequest request) {
        Chapter chapter = getChapterEntity(chapterId);
        validateChapterOwner(userId, chapter);
        chapter.updateName(request.name());
        return ChapterResponse.from(chapter);
    }

    @Override
    public void deleteChapter(Long userId, Long chapterId) {
        Chapter chapter = getChapterEntity(chapterId);
        validateChapterOwner(userId, chapter);
        chapterRepository.delete(chapter);
    }

    private Chapter getChapterEntity(Long chapterId) {
        return chapterRepository
                .findById(chapterId)
                .orElseThrow(() -> new CommonException(ChapterErrorCode.CHAPTER_NOT_FOUND));
    }

    private void validateChapterOwner(Long userId, Chapter chapter) {
        if (!Objects.equals(chapter.getLecture().getTeacher().getId(), userId)) {
            throw new CommonException(UserErrorCode.USER_NOT_AUTHORIZED);
        }
    }
}
