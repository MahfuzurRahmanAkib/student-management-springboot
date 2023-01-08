package com.sms.sb.all_module.service;

import com.sms.sb.all_module.entity.Subject;
import com.sms.sb.all_module.payload.request.SubjectRequestDto;
import com.sms.sb.all_module.payload.response.SubjectViewModel;
import com.sms.sb.all_module.payload.search.SubjectSearchDto;

import java.util.List;

public interface SubjectService {
    SubjectViewModel save(SubjectRequestDto subjectRequestDto);

    Subject update(SubjectRequestDto subjectRequestDto);

    void deleteById(Long id);

    Subject findById(Long id);

    List<SubjectViewModel> findAll();

    List<SubjectViewModel> searchSubject(SubjectSearchDto searchDto);
}
