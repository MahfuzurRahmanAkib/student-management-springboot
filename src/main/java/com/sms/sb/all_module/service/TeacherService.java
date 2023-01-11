package com.sms.sb.all_module.service;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.request.TeacherRequestDto;
import com.sms.sb.all_module.payload.response.TeacherViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;

import java.util.List;

public interface TeacherService {
    TeacherViewModel save(TeacherRequestDto teacherRequestDto);

    Teacher update(TeacherRequestDto teacherRequestDto);

    void deleteById(Long id);

    Teacher findById(Long id);

    List<TeacherViewModel> findAll();

    List<TeacherViewModel> searchTeacher(CommonSearchDto searchDto);
}
