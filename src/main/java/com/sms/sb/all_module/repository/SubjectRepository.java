package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Subject;
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

    @Query("SELECT new com.sms.sb.all_module.payload.response.SubjectViewModel(" +
            "s.id,s.title,s.code,s.departmentId,s.department.code,s.department.name ) " +
            "FROM Subject s WHERE s.title LIKE %:title% AND s.deleted = false"
    )
    List<SubjectViewModel> searchWithTitle(@Param("title") String title);

    @Query("SELECT new com.sms.sb.all_module.payload.response.SubjectViewModel( " +
            "s.id, s.code, s.title, s.departmentId ,s.department.code,s.department.name" +
            ") " +
            "from Teacher as t " +
            "inner join Department as d on d.id = t.departmentId " +
            "inner join Subject as s on d.id = s.departmentId where t.id = :id "
    )
    List<SubjectViewModel> getTeachersInformation(@Param("id") Long teachersId);
}
