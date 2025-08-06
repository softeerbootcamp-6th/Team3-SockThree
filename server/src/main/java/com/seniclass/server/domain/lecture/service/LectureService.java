package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.category.domain.SubCategory;
import com.seniclass.server.domain.category.repository.SubCategoryRepository;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final SubCategoryRepository subCategoryRepository;

    public void createLecture(LectureCreateRequest request) {

        SubCategory subCategory =
                subCategoryRepository
                        .findById(request.subCategoryId())
                        .orElseThrow(
                                () -> new CommonException(LectureErrorCode.SUB_CATEGORY_NOT_FOUND));
        Integer cohort =
                lectureRepository
                        .findTopByNameOrderByCohortDesc(request.name())
                        .map(lecture -> lecture.getCohort() + 1)
                        .orElse(1);

        Lecture lecture =
                Lecture.createLecture(
                        subCategory,
                        request.name(),
                        cohort,
                        request.level(),
                        request.startDate(),
                        request.endDate(),
                        request.maxStudent(),
                        request.fee(),
                        request.instruction(),
                        request.description());

        lectureRepository.save(lecture);
    }

    @Transactional(readOnly = true)
    public Lecture getLecture(Long lectureId) {
        return lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));
    }

    public void updateLecture(Long lectureId, LectureUpdateRequest request) {
        Lecture lecture = getLecture(lectureId);
        SubCategory subCategory =
                subCategoryRepository
                        .findById(request.subCategoryId())
                        .orElseThrow(
                                () -> new CommonException(LectureErrorCode.SUB_CATEGORY_NOT_FOUND));
        lecture.updateLecture(
                subCategory,
                request.name(),
                request.cohort(),
                request.level(),
                request.startDate(),
                request.endDate(),
                request.maxStudent(),
                request.fee(),
                request.instruction(),
                request.description());
    }

    public void deleteLecture(Long lectureId) {
        lectureRepository.deleteById(lectureId);
    }
}
