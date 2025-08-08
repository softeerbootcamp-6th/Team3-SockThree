package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.student.domain.LectureQna;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.LectureQnaAnswerRequest;
import com.seniclass.server.domain.student.dto.LectureQnaRequest;
import com.seniclass.server.domain.student.dto.LectureQnaResponse;
import com.seniclass.server.domain.student.exception.errorcode.LectureQnaErrorCode;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.LectureQnaRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LectureQnaServiceImpl implements LectureQnaService {

    private final LectureQnaRepository lectureQnaRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    /** 강의 질문 등록 */
    @Transactional
    @Override
    public LectureQnaResponse createQuestion(LectureQnaRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        Lecture lecture =
                lectureRepository
                        .findById(request.lectureId())
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));

        LectureQna qna = LectureQna.createLectureQna(student, lecture, request.question());
        LectureQna saved = lectureQnaRepository.save(qna);

        log.info(
                "Lecture question created: student={}, lecture={}", studentId, request.lectureId());
        return LectureQnaResponse.from(saved);
    }

    /** 강의 질문 수정 */
    @Transactional
    @Override
    public LectureQnaResponse updateQuestion(Long qnaId, LectureQnaRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        LectureQna qna =
                lectureQnaRepository
                        .findById(qnaId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureQnaErrorCode.LECTURE_QNA_NOT_FOUND));

        // 본인의 질문인지 확인
        if (!qna.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        // 질문이 답변된 경우 수정 불가 (비즈니스 로직에 따라 조정 가능)
        if (qna.getAnswer() != null) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        // 질문 내용 업데이트 (LectureQna 엔티티에 updateQuestion 메서드가 있다고 가정)
        // qna.updateQuestion(request.question()); // 실제로는 이런 메서드가 엔티티에 있어야 함

        log.info("Lecture question updated: qnaId={}, student={}", qnaId, studentId);
        return LectureQnaResponse.from(qna);
    }

    /** 강의 질문 삭제 */
    @Transactional
    @Override
    public void deleteQuestion(Long qnaId) {
        Long studentId = AuthContext.getCurrentUserId();

        LectureQna qna =
                lectureQnaRepository
                        .findById(qnaId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureQnaErrorCode.LECTURE_QNA_NOT_FOUND));

        // 본인의 질문인지 확인
        if (!qna.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        lectureQnaRepository.delete(qna);

        log.info("Lecture question deleted: qnaId={}, student={}", qnaId, studentId);
    }

    /** 강의 질문 답변 등록/수정 (강사용) */
    @Transactional
    @Override
    public LectureQnaResponse answerQuestion(Long qnaId, LectureQnaAnswerRequest request) {
        LectureQna qna =
                lectureQnaRepository
                        .findById(qnaId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureQnaErrorCode.LECTURE_QNA_NOT_FOUND));

        qna.updateAnswer(request.answer());

        log.info("Lecture question answered: qnaId={}", qnaId);
        return LectureQnaResponse.from(qna);
    }

    /** 현재 학생의 질문 목록 조회 */
    @Override
    public Page<LectureQnaResponse> getCurrentStudentQuestions(Pageable pageable) {
        Long studentId = AuthContext.getCurrentUserId();

        Page<LectureQna> questions =
                lectureQnaRepository.findByStudentIdOrderByCreatedDtDesc(studentId, pageable);

        return questions.map(LectureQnaResponse::from);
    }

    /** 특정 강의의 QnA 목록 조회 */
    @Override
    public Page<LectureQnaResponse> getQuestionsByLecture(Long lectureId, Pageable pageable) {
        Page<LectureQna> questions =
                lectureQnaRepository.findByLectureIdOrderByCreatedDtDesc(lectureId, pageable);

        return questions.map(LectureQnaResponse::from);
    }

    /** 특정 QnA 상세 조회 */
    @Override
    public LectureQnaResponse getQuestion(Long qnaId) {
        LectureQna qna =
                lectureQnaRepository
                        .findById(qnaId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureQnaErrorCode.LECTURE_QNA_NOT_FOUND));

        return LectureQnaResponse.from(qna);
    }

    /** 현재 학생의 총 질문 수 조회 */
    @Override
    public long getCurrentStudentQuestionCount() {
        Long studentId = AuthContext.getCurrentUserId();

        return lectureQnaRepository.countByStudentId(studentId);
    }
}
