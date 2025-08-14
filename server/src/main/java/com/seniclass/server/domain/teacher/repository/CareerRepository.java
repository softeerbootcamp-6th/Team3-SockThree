package com.seniclass.server.domain.teacher.repository;

import com.seniclass.server.domain.teacher.domain.Career;
import com.seniclass.server.domain.teacher.enums.Type;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    // * 강사 ID로 커리어 내역을 리스트로 조회 */
    @Query("select c.name from Career c where c.teacher.id = :teacherId")
    List<String> findCareerNameByTeacherId(Long teacherId);

    // * 강사 ID와 타입으로 커리어 개수 조회 */
    Integer countByTeacherIdAndType(Long id, Type type);
}
