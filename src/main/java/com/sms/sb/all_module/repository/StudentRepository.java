package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.search.StudentSearchResponse;
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

    @Query(value = "SELECT new com.sms.sb.all_module.payload.search.StudentSearchResponse ( " +
            "st.id, " +
            "st.firstName, " +
            "st.lastName, " +
            "st.gender, " +
            "st.email, " +
            "st.phone, " +
            "s.title, " +
            "s.code, " +
            "d.code, " +
            "d.name" +
            ") " +
            "from Student as st " +
            "inner join Department as d on d.id = st.departmentId " +
            "inner join Subject as s on s.departmentId = d.id " +
            "where st.firstName like %:firstName% and st.deleted = false"
    )
    List<StudentSearchResponse> searchWithName(@Param("firstName") String firstName);
}
