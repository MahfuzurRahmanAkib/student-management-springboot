package com.sms.sb.all_module.service;

import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.request.StudentRequestDto;
import com.sms.sb.all_module.payload.response.StudentViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;

import java.util.List;

public interface StudentService {
    StudentViewModel save(StudentRequestDto studentRequestDto);

    Student update(StudentRequestDto studentRequestDto);

    void deleteById(Long id);

    Student findById(Long id);

    List<StudentViewModel> findAll();

    List<StudentViewModel> searchStudent(CommonSearchDto studentSearchDto);
}
