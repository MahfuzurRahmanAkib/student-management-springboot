package com.sms.sb.all_module.service.impl;

import com.sms.sb.all_module.entity.Department;
import com.sms.sb.all_module.payload.request.DepartmentRequestDto;
import com.sms.sb.all_module.payload.response.DepartmentViewModel;
import com.sms.sb.all_module.payload.response.SubjectDepartmentCombinedViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;
import com.sms.sb.all_module.repository.DepartmentRepository;
import com.sms.sb.all_module.service.DepartmentService;
import com.sms.sb.all_module.service.SubjectService;
import com.sms.sb.common.code_tracker.CodeTrackingService;
import com.sms.sb.common.code_tracker.CodeType;
import com.sms.sb.common.constant.ApplicationConstant;
import com.sms.sb.common.constant.ErrorId;
import com.sms.sb.common.exception.StudentManagementException;
import com.sms.sb.common.util.CaseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;
    private SubjectService subjectService;
    private CodeTrackingService codeTrackingService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 @Lazy SubjectService subjectService,
                                 CodeTrackingService codeTrackingService) {
        super();
        this.departmentRepository = departmentRepository;
        this.subjectService = subjectService;
        this.codeTrackingService = codeTrackingService;
    }

    @Override
    public DepartmentViewModel save(DepartmentRequestDto departmentRequestDto) {
        Department department = new Department();
        convertToSaveEntity(department, departmentRequestDto);
        Department savedDepartment;
        try {
            savedDepartment = departmentRepository.save(department);
        } catch (Exception exception) {
            LOGGER.error("Data not saved : {}", departmentRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_SAVED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return convertToViewModel(savedDepartment);
    }

    @Override
    public Department update(DepartmentRequestDto departmentRequestDto) {
        if (Objects.isNull(departmentRequestDto.getId())) {
            LOGGER.error("Information ID is null: {}", departmentRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_FOUND, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        Department savedDepartment = findById(departmentRequestDto.getId());
        try {
            return departmentRepository.save(convertToUpdateEntity(savedDepartment, departmentRequestDto));
        } catch (Exception e) {
            LOGGER.error("Information not updated : {}", departmentRequestDto);
            if (e instanceof StudentManagementException) {
                throw e;
            }
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_UPDATED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    @Override
    public void deleteById(Long id) {
        Department department = findById(id);
        department.setDeleted(Boolean.TRUE);
        try {
            departmentRepository.save(department);
        } catch (Exception e) {
            LOGGER.error("Information not deleted with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.FAIL_TO_DELETE, HttpStatus.INTERNAL_SERVER_ERROR, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    @Override
    public Department findById(Long id) {
        if (Objects.isNull(id)) {
            LOGGER.error("Id is required !!");
            throw new StudentManagementException(
                    ErrorId.ID_IS_REQUIRED, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return departmentRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> {
            LOGGER.error("Information does not exists with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        });
    }

    @Override
    public List<DepartmentViewModel> findAll() {
        List<Department> departmentList = departmentRepository.findAllByDeletedFalse();
        return departmentList.stream().map(this::convertToViewModel).collect(Collectors.toList());

    }

    private Department convertToSaveEntity(Department department, DepartmentRequestDto departmentRequestDto) {
        department.setName(CaseConverter.capitalizeAllCharacter(departmentRequestDto.getName()));
        department.setCode(codeTrackingService.generateUniqueCodeNo(CodeType.DEPARTMENT));
        return department;
    }

    private Department convertToUpdateEntity(Department department, DepartmentRequestDto departmentRequestDto) {
        department.setName(CaseConverter.capitalizeAllCharacter(departmentRequestDto.getName()));
        return department;
    }

    public DepartmentViewModel convertToViewModel(Department department) {
        DepartmentViewModel viewModel = new DepartmentViewModel();
        viewModel.setId(department.getId());
        viewModel.setCode(department.getCode());
        viewModel.setName(department.getName());
        viewModel.setSubjectViewModels(subjectService.getSubjectByDepartmentId(department.getId()));
        return viewModel;
    }

    @Override
    public List<DepartmentViewModel> searchDepartment(CommonSearchDto searchDto) {
        return departmentRepository.searchWithName(searchDto.getName());
    }

    public List<SubjectDepartmentCombinedViewModel> findByStudentId(Long id){
        return departmentRepository.findByStudentId(id);
    }

    public List<SubjectDepartmentCombinedViewModel> findByTeacherId(Long id){
        return departmentRepository.findByTeacherId(id);
    }
}
