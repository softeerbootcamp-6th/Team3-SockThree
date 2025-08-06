package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.category.domain.SubCategory;
import com.seniclass.server.domain.category.repository.SubCategoryRepository;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.LectureResponse;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.teacher.domain.Teacher;
import com.seniclass.server.domain.teacher.repository.TeacherRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import com.seniclass.server.global.exception.errorcode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public LectureResponse createLecture(Long userId, LectureCreateRequest request) {
        request.validateDateOrder();

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

        Teacher teacher =
                teacherRepository
                        .findById(userId)
                        .orElseThrow(() -> new CommonException(UserErrorCode.USER_NOT_FOUND));

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
                        request.description(),
                        teacher);

        return LectureResponse.from(lectureRepository.save(lecture));
    }

    @Override
    @Transactional(readOnly = true)
    public LectureResponse getLecture(Long lectureId) {
        Lecture lecture = getLectureEntity(lectureId);
        return LectureResponse.from(lecture);
    }

    @Override
    public LectureResponse updateLecture(Long lectureId, LectureUpdateRequest request) {
        request.validateDateOrder();

        Lecture lecture = getLectureEntity(lectureId);
        SubCategory subCategory =
                subCategoryRepository
                        .findById(request.subCategoryId())
                        .orElseThrow(
                                () -> new CommonException(LectureErrorCode.SUB_CATEGORY_NOT_FOUND));
        lecture.updateLecture(
                subCategory,
                request.level(),
                request.startDate(),
                request.endDate(),
                request.maxStudent(),
                request.instruction(),
                request.description());

        return LectureResponse.from(lecture);
    }

    public void deleteLecture(Long lectureId) {
        lectureRepository.deleteById(lectureId);
    }

    private Lecture getLectureEntity(Long lectureId) {
        return lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));
    }
}
