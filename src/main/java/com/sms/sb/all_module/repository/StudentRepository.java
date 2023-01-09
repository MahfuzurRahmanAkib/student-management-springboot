package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.response.StudentViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByDeletedFalse();

    Optional<Student> findByIdAndDeletedFalse(Long id);

    @Query("SELECT new com.sms.sb.all_module.payload.response.StudentViewModel(" +
            "p.id,p.firstName, p.lastName, p.email, p.phone ,p.departmentId, p.department.code ) " +
            "FROM Student p WHERE p.firstName LIKE %:firstName% AND p.deleted = false"
    )
    List<StudentViewModel> searchWithName(@Param("firstName") String firstName);
}
