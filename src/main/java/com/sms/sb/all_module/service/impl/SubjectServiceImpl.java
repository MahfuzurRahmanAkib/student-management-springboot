package com.sms.sb.all_module.service.impl;

import com.sms.sb.all_module.entity.Subject;
import com.sms.sb.all_module.payload.request.SubjectRequestDto;
import com.sms.sb.all_module.payload.response.SubjectViewModel;
import com.sms.sb.all_module.payload.search.SubjectSearchDto;
import com.sms.sb.all_module.repository.SubjectRepository;
import com.sms.sb.all_module.service.DepartmentService;
import com.sms.sb.all_module.service.SubjectService;
import com.sms.sb.all_module.service.TeacherService;
import com.sms.sb.common.constant.ApplicationConstant;
import com.sms.sb.common.constant.ErrorId;
import com.sms.sb.common.exception.StudentManagementException;
import com.sms.sb.common.util.CaseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;
    private DepartmentService departmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectServiceImpl.class);

    public SubjectServiceImpl(SubjectRepository subjectRepository, DepartmentService departmentService) {
        super();
        this.subjectRepository = subjectRepository;
        this.departmentService = departmentService;
    }

    @Override
    public SubjectViewModel save(SubjectRequestDto subjectRequestDto) {
        Subject subject = new Subject();
        convertToEntity(subject, subjectRequestDto);
        Subject savedSubject;
        try {
            savedSubject = subjectRepository.save(subject);
        } catch (Exception exception) {
            LOGGER.error("Data not saved : {}", subjectRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_SAVED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return convertToViewModel(savedSubject);
    }

    @Override
    public Subject update(SubjectRequestDto subjectRequestDto) {
        if (Objects.isNull(subjectRequestDto.getId())) {
            LOGGER.error("ID is null: {}", subjectRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_FOUND, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        Subject savedSubject = findById(subjectRequestDto.getId());
        try {
            return subjectRepository.save(convertToEntity(savedSubject, subjectRequestDto));
        } catch (Exception e) {
            LOGGER.error("Information not updated : {}", subjectRequestDto);
            if (e instanceof StudentManagementException) {
                throw e;
            }
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_UPDATED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    @Override
    public void deleteById(Long id) {
        Subject subject = findById(id);
        subject.setDeleted(Boolean.TRUE);
        try {
            subjectRepository.save(subject);
        } catch (Exception e) {
            LOGGER.error("Information not deleted with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.FAIL_TO_DELETE, HttpStatus.INTERNAL_SERVER_ERROR, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    @Override
    public Subject findById(Long id) {
        if (Objects.isNull(id)) {
            LOGGER.error("Id is required !!");
            throw new StudentManagementException(
                    ErrorId.ID_IS_REQUIRED, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return subjectRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> {
            LOGGER.error("Information does not exists with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        });
    }

    @Override
    public List<SubjectViewModel> findAll() {
        List<Subject> subjectList = subjectRepository.findAllByDeletedFalse();
        return subjectList.stream().map(this::convertToViewModel).collect(Collectors.toList());
    }

    @Override
    public List<SubjectViewModel> searchSubject(SubjectSearchDto searchDto) {
        return subjectRepository.searchWithTitle(searchDto.getTitle());
    }

    public SubjectViewModel convertToViewModel(Subject savedSubject) {
        SubjectViewModel viewModel = new SubjectViewModel();
        viewModel.setId(savedSubject.getId());
        viewModel.setTitle(savedSubject.getTitle());
        viewModel.setCode(savedSubject.getCode());
        viewModel.setDepartmentId(savedSubject.getDepartmentId());
        viewModel.setDepartmentCode(savedSubject.getDepartment().getCode());
        viewModel.setDepartmentName(savedSubject.getDepartment().getName());
        return viewModel;
    }

    private Subject convertToEntity(Subject subject, SubjectRequestDto subjectRequestDto) {
        subject.setTitle(CaseConverter.capitalizeFirstCharacter(subjectRequestDto.getTitle()));
        subject.setCode(CaseConverter.capitalizeAllCharacter(subjectRequestDto.getCode()));
        if (Objects.nonNull(subjectRequestDto.getDepartmentId())){
            subject.setDepartment(departmentService.findById(subjectRequestDto.getDepartmentId()));
        }
        return subject;
    }

}
