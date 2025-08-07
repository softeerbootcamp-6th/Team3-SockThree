package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.Student;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /** 이메일로 학생 조회 */
    Optional<Student> findByEmail(String email);

    /** 이메일 존재 여부 확인 */
    boolean existsByEmail(String email);

    /** 이름으로 학생 검색 (부분 일치, 대소문자 무관) */
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
