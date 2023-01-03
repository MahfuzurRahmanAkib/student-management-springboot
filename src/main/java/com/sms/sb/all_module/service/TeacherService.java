package com.sms.sb.all_module.service;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.request.TeacherResponseDto;
import com.sms.sb.all_module.payload.response.TeacherViewModel;
import com.sms.sb.all_module.payload.search.TeacherSearchDto;

import java.util.List;

public interface TeacherService {
    TeacherViewModel save(TeacherResponseDto teacherResponseDto);

    Teacher update(TeacherResponseDto teacherResponseDto);

    void deleteById(Long id);

    Teacher findById(Long id);

    List<TeacherViewModel> findAll();

    List<TeacherViewModel> searchTeacher(TeacherSearchDto teacherSearchDto);
}
