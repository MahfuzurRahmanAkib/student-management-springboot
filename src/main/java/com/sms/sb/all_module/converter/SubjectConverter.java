package com.sms.sb.all_module.converter;

import com.sms.sb.all_module.entity.Subject;
import com.sms.sb.all_module.payload.request.SubjectRequestDto;
import com.sms.sb.all_module.payload.response.SubjectViewModel;
import com.sms.sb.common.util.CaseConverter;

public class SubjectConverter {
    public static SubjectViewModel convertToViewModel(Subject savedSubject) {
        SubjectViewModel viewModel = new SubjectViewModel();
        viewModel.setId(savedSubject.getId());
        viewModel.setTitle(savedSubject.getTitle());
        viewModel.setCode(savedSubject.getCode());
        return viewModel;
    }

    public static Subject convertToEntity(Subject subject, SubjectRequestDto subjectRequestDto) {
        subject.setTitle(CaseConverter.capitalizeFirstCharacter(subjectRequestDto.getTitle()));
        subject.setCode(CaseConverter.capitalizeAllCharacter(subjectRequestDto.getCode()));
        return subject;
    }
}
