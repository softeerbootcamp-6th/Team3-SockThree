-- AssignmentSubmission 테이블에 feedback 컬럼 추가
ALTER TABLE assignment_submission ADD COLUMN feedback TEXT;

-- Review 테이블에 unique 제약조건 추가 (학생당 강의별 리뷰 하나만 허용)
ALTER TABLE review ADD CONSTRAINT uk_review_student_lecture UNIQUE (student_id, lecture_id);
