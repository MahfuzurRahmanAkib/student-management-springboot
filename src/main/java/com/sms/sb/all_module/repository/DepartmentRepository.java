package com.sms.sb.all_module.repository;

import com.sms.sb.all_module.entity.Department;
import com.sms.sb.all_module.payload.response.DepartmentViewModel;
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

    @Query("SELECT new com.sms.sb.all_module.payload.response.DepartmentViewModel(" +
            "d.id,d.code,d.name ) " +
            "FROM Department d WHERE d.name LIKE %:name% AND d.deleted = false"
    )
    List<DepartmentViewModel> searchWithName(@Param("name") String name);
}
