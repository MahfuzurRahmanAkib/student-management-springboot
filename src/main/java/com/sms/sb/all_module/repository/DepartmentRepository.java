package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Department;
import com.sms.sb.all_module.payload.response.DepartmentViewModel;
import com.sms.sb.all_module.payload.response.SubjectDepartmentCombinedViewModel;
import com.sms.sb.all_module.payload.search.DepartmentSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByIdAndDeletedFalse(Long id);

    List<Department> findAllByDeletedFalse();

    @Query(value = "SELECT new com.sms.sb.all_module.payload.search.DepartmentSearchResponse ( " +
            "d.id, " +
            "s.title, " +
            "s.code, " +
            "d.code, " +
            "d.name" +
            ") " +
            "from Department as d " +
            "inner join Subject as s on s.departmentId = d.id " +
            "where d.code like %:code% and d.deleted = false"
    )
    List<DepartmentSearchResponse> searchWithName(@Param("code") String code);

    @Query("SELECT new com.sms.sb.all_module.payload.response.DepartmentViewModel (" +
            "d.id,d.code,d.name ) " +
            "from Student as s " +
            "inner join Department as d on s.departmentId = d.id " +
            "inner join Subject as sub on d.id = sub.id where s.id = :id "
    )
    List<DepartmentViewModel> findByStudentId(Long id);

    @Query("SELECT new com.sms.sb.all_module.payload.response.DepartmentViewModel(" +
            "d.id,d.code,d.name ) " +
            "from Teacher as t " +
            "inner join Department as d on t.departmentId = d.id " +
            "inner join Subject as sub on d.id = sub.id where t.id = :id "
    )
    List<DepartmentViewModel> findByTeacherId(Long id);
}
