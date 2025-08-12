package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.student.dto.LectureQnaAnswerRequest;
import com.seniclass.server.domain.student.dto.LectureQnaRequest;
import com.seniclass.server.domain.student.dto.LectureQnaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureQnaService {

    /** 강의 질문 등록 */
    LectureQnaResponse createQuestion(LectureQnaRequest request);

    /** 강의 질문 수정 */
    LectureQnaResponse updateQuestion(Long qnaId, LectureQnaRequest request);

    /** 강의 질문 삭제 */
    void deleteQuestion(Long qnaId);

    /** 강의 질문 답변 등록/수정 (강사용) */
    LectureQnaResponse answerQuestion(Long qnaId, LectureQnaAnswerRequest request);

    /** 현재 학생의 질문 목록 조회 */
    Page<LectureQnaResponse> getCurrentStudentQuestions(Pageable pageable);

    /** 특정 강의의 QnA 목록 조회 */
    Page<LectureQnaResponse> getQuestionsByLecture(Long lectureId, Pageable pageable);

    /** 특정 QnA 상세 조회 */
    LectureQnaResponse getQuestion(Long qnaId);

    /** 현재 학생의 총 질문 수 조회 */
    long getCurrentStudentQuestionCount();
}
