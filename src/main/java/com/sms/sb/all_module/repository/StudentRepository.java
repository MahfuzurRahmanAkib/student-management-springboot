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
            "s.id,s.firstName, s.lastName,s.gender, s.email, s.phone) " +
            "FROM Student s WHERE s.firstName LIKE %:firstName% AND s.deleted = false"
    )
    List<StudentViewModel> searchWithName(@Param("firstName") String firstName);
}
