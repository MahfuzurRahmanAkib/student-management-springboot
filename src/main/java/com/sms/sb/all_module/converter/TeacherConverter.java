package com.sms.sb.all_module.converter;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.request.TeacherResponseDto;
import com.sms.sb.all_module.payload.response.TeacherViewModel;
import com.sms.sb.common.util.CaseConverter;

public class TeacherConverter {
    public static TeacherViewModel convertToViewModel(Teacher teacher) {
        TeacherViewModel viewModel = new TeacherViewModel();
        viewModel.setId(teacher.getId());
        viewModel.setFirstName(teacher.getFirstName());
        viewModel.setLastName(teacher.getLastName());
        viewModel.setEmail(teacher.getEmail());
        viewModel.setPhone(teacher.getPhone());
        viewModel.setSubject(teacher.getSubject());
        return viewModel;
    }

    public static Teacher convertToEntity(Teacher teacher, TeacherResponseDto teacherResponseDto) {
        teacher.setFirstName(CaseConverter.capitalizeFirstCharacter(teacherResponseDto.getFirstName()));
        teacher.setLastName(CaseConverter.capitalizeFirstCharacter(teacherResponseDto.getLastName()));
        teacher.setSubject(CaseConverter.capitalizeFirstCharacter(teacherResponseDto.getSubject()));
        teacher.setEmail(CaseConverter.uncapitalizeAllCharacter(teacherResponseDto.getEmail()));
        teacher.setPhone(teacherResponseDto.getPhone());
        return teacher;
    }
}
