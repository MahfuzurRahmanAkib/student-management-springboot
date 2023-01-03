package com.sms.sb.all_module.converter;

import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.request.StudentRequestDto;
import com.sms.sb.common.util.CaseConverter;
import com.sms.sb.all_module.payload.response.StudentViewModel;

public class StudentConverter {
    public static StudentViewModel convertToViewModel(Student savedStudent) {
        StudentViewModel viewModel = new StudentViewModel();
        viewModel.setId(savedStudent.getId());
        viewModel.setFirstName(savedStudent.getFirstName());
        viewModel.setLastName(savedStudent.getLastName());
        viewModel.setEmail(savedStudent.getEmail());
        viewModel.setPhone(savedStudent.getPhone());
        return viewModel;
    }

    public static Student convertToEntity(Student student, StudentRequestDto studentRequestDto) {
        student.setFirstName(CaseConverter.capitalizeFirstCharacter(studentRequestDto.getFirstName()));
        student.setLastName(CaseConverter.capitalizeFirstCharacter(studentRequestDto.getLastName()));
        student.setEmail(CaseConverter.uncapitalizeAllCharacter(studentRequestDto.getEmail()));
        student.setPhone(studentRequestDto.getPhone());
        return student;
    }
}
