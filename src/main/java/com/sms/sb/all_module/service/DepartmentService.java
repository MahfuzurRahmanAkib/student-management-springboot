package com.sms.sb.all_module.service;

import com.sms.sb.all_module.entity.Department;
import com.sms.sb.all_module.payload.request.DepartmentRequestDto;
import com.sms.sb.all_module.payload.response.DepartmentViewModel;
import com.sms.sb.all_module.payload.search.DepartmentSearchDto;

import java.util.List;

public interface DepartmentService {
    DepartmentViewModel save(DepartmentRequestDto departmentRequestDto);

    Department update(DepartmentRequestDto departmentRequestDto);

    void deleteById(Long id);

    Department findById(Long id);

    List<DepartmentViewModel> findAll();

    List<DepartmentViewModel> searchDepartment(DepartmentSearchDto searchDto);
}