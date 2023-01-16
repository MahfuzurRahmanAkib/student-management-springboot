package com.sms.sb.all_module.service;

import com.sms.sb.all_module.entity.Subject;
import com.sms.sb.all_module.payload.request.SubjectRequestDto;
import com.sms.sb.all_module.payload.response.SubjectDepartmentCombinedViewModel;
import com.sms.sb.all_module.payload.response.SubjectViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;

import java.util.List;

public interface SubjectService {
    SubjectDepartmentCombinedViewModel save(SubjectRequestDto subjectRequestDto);

    Subject update(SubjectRequestDto subjectRequestDto);

    void deleteById(Long id);

    Subject findById(Long id);

    List<SubjectDepartmentCombinedViewModel> findAll();

    List<SubjectDepartmentCombinedViewModel> searchSubject(CommonSearchDto searchDto);

    List<SubjectViewModel> getSubjectByDepartmentId(Long id);
}
