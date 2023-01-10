package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.response.TeacherViewModel;
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

    @Query("SELECT new com.sms.sb.all_module.payload.response.TeacherViewModel(" +
            "p.id, p.firstName, p.lastName, p.email, p.phone)" +
            "FROM Teacher p WHERE p.firstName LIKE %:firstName% AND p.deleted = false"
    )
    List<TeacherViewModel> searchWithName(@Param("firstName") String firstName);
}
