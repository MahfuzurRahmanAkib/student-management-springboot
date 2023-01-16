package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Subject;
import com.sms.sb.all_module.payload.response.SubjectDepartmentCombinedViewModel;
import com.sms.sb.all_module.payload.response.SubjectViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByIdAndDeletedFalse(Long id);

    List<Subject> findAllByDeletedFalse();

    @Query("SELECT new com.sms.sb.all_module.payload.response.SubjectDepartmentCombinedViewModel(" +
            "s.id,s.title,s.code,s.departmentId,s.department.code,s.department.name ) " +
            "FROM Subject s WHERE s.title LIKE %:title% AND s.deleted = false"
    )
    List<SubjectDepartmentCombinedViewModel> searchWithTitle(@Param("title") String title);

    @Query("SELECT new com.sms.sb.all_module.payload.response.SubjectViewModel( " +
            "s.id, s.code, s.title" +
            ") " +
            "from Department as d " +
            "inner join Subject as s on d.id = s.departmentId where d.id = :id "
    )
    List<SubjectViewModel> getSubjectByDepartmentId(@Param("id") Long departmentId);
}
