package com.sms.sb.all_module.converter;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.request.TeacherRequestDto;
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

    public static Teacher convertToEntity(Teacher teacher, TeacherRequestDto teacherRequestDto) {
        teacher.setFirstName(CaseConverter.capitalizeFirstCharacter(teacherRequestDto.getFirstName()));
        teacher.setLastName(CaseConverter.capitalizeFirstCharacter(teacherRequestDto.getLastName()));
        teacher.setSubject(CaseConverter.capitalizeFirstCharacter(teacherRequestDto.getSubject()));
        teacher.setEmail(CaseConverter.uncapitalizeAllCharacter(teacherRequestDto.getEmail()));
        teacher.setPhone(teacherRequestDto.getPhone());
        return teacher;
    }
}
