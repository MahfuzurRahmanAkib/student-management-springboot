package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.search.TeacherSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByDeletedFalse();

    Optional<Teacher> findByIdAndDeletedFalse(Long id);

    @Query(value = "SELECT new com.sms.sb.all_module.payload.search.TeacherSearchResponse ( " +
            "t.id, " +
            "t.firstName, " +
            "t.lastName, " +
            "t.email, " +
            "t.phone, " +
            "s.title, " +
            "s.code, " +
            "d.code, " +
            "d.name" +
            ") " +
            "from Teacher as t " +
            "inner join Department as d on d.id = t.departmentId " +
            "inner join Subject as s on s.departmentId = d.id " +
            "where t.firstName like %:firstName% and t.deleted = false"
    )
    List<TeacherSearchResponse> searchWithName(@Param("firstName") String firstName);
}
