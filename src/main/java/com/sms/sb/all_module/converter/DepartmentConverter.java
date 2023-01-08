package com.sms.sb.all_module.converter;

import com.sms.sb.all_module.entity.Department;
import com.sms.sb.all_module.payload.request.DepartmentRequestDto;
import com.sms.sb.all_module.payload.response.DepartmentViewModel;
import com.sms.sb.common.util.CaseConverter;

public class DepartmentConverter {
    public static Department convertToEntity(Department department, DepartmentRequestDto departmentRequestDto) {
        department.setName(CaseConverter.capitalizeFirstCharacter(departmentRequestDto.getName()));
        department.setCode(CaseConverter.capitalizeAllCharacter(departmentRequestDto.getCode()));
        return department;
    }

    public static DepartmentViewModel convertToViewModel(Department savedDepartment) {
        DepartmentViewModel viewModel = new DepartmentViewModel();
        viewModel.setId(savedDepartment.getId());
        viewModel.setCode(savedDepartment.getCode());
        viewModel.setName(savedDepartment.getName());
        return viewModel;
    }
}
