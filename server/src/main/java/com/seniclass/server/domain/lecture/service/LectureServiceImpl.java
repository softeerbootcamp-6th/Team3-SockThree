package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.category.domain.SubCategory;
import com.seniclass.server.domain.category.repository.SubCategoryRepository;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.domain.UploadTime;
import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.LectureInfoWidgetResponse;
import com.seniclass.server.domain.lecture.dto.response.LectureResponse;
import com.seniclass.server.domain.lecture.dto.response.UploadTimeResponse;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.teacher.domain.Teacher;
import com.seniclass.server.domain.teacher.repository.TeacherRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import com.seniclass.server.global.exception.errorcode.UserErrorCode;
import com.seniclass.server.global.service.FileStorageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LectureServiceImpl implements LectureService {

    private final UploadTimeService uploadTimeService;
    private final LectureRepository lectureRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final TeacherRepository teacherRepository;
    private final WidgetSettingService widgetSettingService;
    private final FileStorageService fileStorageService;

    @Override
    public LectureResponse createLecture(
            Long userId, LectureCreateRequest request, MultipartFile file) {
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

        String imageKey = fileStorageService.storeFile(file, "lectures/images");
        log.info("파일 저장 완료: S3 key = {}", imageKey);

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
                        imageKey,
                        teacher);

        LectureResponse savedLecture = LectureResponse.from(lectureRepository.save(lecture));

        request.uploadTimeList()
                .forEach(
                        uploadTime -> {
                            uploadTimeService.createUploadTime(uploadTime, lecture);
                        });

        widgetSettingService.createDefaultWidgetSettings(lecture);

        return savedLecture;
    }

    @Override
    @Transactional(readOnly = true)
    public LectureResponse getLecture(Long lectureId) {
        Lecture lecture = getLectureEntity(lectureId);
        String presignedImageURL = fileStorageService.getFileUrl(lecture.getImageKey());

        return LectureResponse.from(lecture, presignedImageURL);
    }

    @Override
    public LectureResponse updateLecture(
            Long userId, Long lectureId, LectureUpdateRequest request, MultipartFile file) {

        Lecture lecture = getLectureEntity(lectureId);

        if (!lecture.getTeacher().getId().equals(userId)) {
            throw new CommonException(UserErrorCode.USER_NOT_AUTHORIZED);
        }

        SubCategory subCategory =
                subCategoryRepository
                        .findById(request.subCategoryId())
                        .orElseThrow(
                                () -> new CommonException(LectureErrorCode.SUB_CATEGORY_NOT_FOUND));

        String imageKey = lecture.getImageKey();
        if (file != null && !file.isEmpty()) {
            fileStorageService.deleteFile(imageKey);
            imageKey = fileStorageService.storeFile(file, "lectures/images");
            log.info("파일 저장 완료: S3 key = {}", imageKey);
        }

        lecture.updateLecture(
                subCategory,
                request.level(),
                request.startDate(),
                request.endDate(),
                request.maxStudent(),
                request.instruction(),
                request.description(),
                imageKey);

        return LectureResponse.from(lecture);
    }

    public void deleteLecture(Long lectureId) {
        lectureRepository.deleteById(lectureId);
    }

    public LectureInfoWidgetResponse getLectureInfo(Long lectureId) {
        Lecture lecture = getLectureEntity(lectureId);

        List<UploadTime> uploadTimeList = uploadTimeService.getAllUploadTimesEntity(lecture);
        List<UploadTimeResponse> uploadTimeResponses =
                uploadTimeList.stream().map(UploadTimeResponse::from).toList();

        return LectureInfoWidgetResponse.from(lecture, uploadTimeResponses);
    }

    private Lecture getLectureEntity(Long lectureId) {
        return lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));
    }
}
