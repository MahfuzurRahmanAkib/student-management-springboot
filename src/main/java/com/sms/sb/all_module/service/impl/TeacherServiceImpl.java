package com.sms.sb.all_module.service.impl;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.search.TeacherSearchResponse;
import com.sms.sb.all_module.payload.request.TeacherRequestDto;
import com.sms.sb.all_module.payload.response.TeacherViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;
import com.sms.sb.all_module.repository.TeacherRepository;
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
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final SubjectService subjectService;
    private final DepartmentService departmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    /**
     * constructor
     *
     * @param teacherRepository {@link TeacherRepository}
     * @param subjectService    {@link SubjectService}
     * @param departmentService {@link DepartmentService}
     */
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              SubjectService subjectService,
                              DepartmentService departmentService) {
        super();
        this.teacherRepository = teacherRepository;
        this.subjectService = subjectService;
        this.departmentService = departmentService;
    }

    /**
     * save method
     *
     * @param teacherRequestDto {@link TeacherRequestDto}
     * @return message
     */
    @Override
    public TeacherViewModel save(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = new Teacher();
        convertToEntity(teacher, teacherRequestDto);
        Teacher savedTeacher;
        try {
            savedTeacher = teacherRepository.save(teacher);
        } catch (Exception exception) {
            LOGGER.error("Information data not saved : {}", teacherRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_SAVED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return convertToViewModel(savedTeacher);
    }

    /**
     * update method
     *
     * @param teacherRequestDto {@link TeacherRequestDto}
     * @return message
     */
    @Override
    public Teacher update(TeacherRequestDto teacherRequestDto) {
        if (Objects.isNull(teacherRequestDto.getId())) {
            LOGGER.error("Information ID is null: {}", teacherRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_FOUND, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        Teacher teacher = findById(teacherRequestDto.getId());
        try {
            return teacherRepository.save(convertToEntity(teacher, teacherRequestDto));
        } catch (Exception exception) {
            LOGGER.error("Information information not updated : {}", teacherRequestDto);
            if (exception instanceof StudentManagementException) {
                throw exception;
            }
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_UPDATED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    /**
     * Single get method
     *
     * @param id required id
     * @return response
     */
    @Override
    public Teacher findById(Long id) {
        if (Objects.isNull(id)) {
            LOGGER.error("Information id is required !!");
            throw new StudentManagementException(
                    ErrorId.ID_IS_REQUIRED, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return teacherRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> {
            LOGGER.error("Student does not exists with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        });
    }

    /**
     * Delete method
     *
     * @param id required id
     */
    @Override
    public void deleteById(Long id) {
        Teacher teacher = findById(id);
        teacher.setDeleted(Boolean.TRUE);
        try {
            teacherRepository.save(teacher);
        } catch (Exception e) {
            LOGGER.error("Information not deleted with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.FAIL_TO_DELETE, HttpStatus.INTERNAL_SERVER_ERROR, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    /**
     * find all method
     *
     * @return response
     */
    @Override
    public List<TeacherViewModel> findAll() {
        List<Teacher> teacherList = teacherRepository.findAllByDeletedFalse();
        return teacherList.stream().map(this::convertToViewModel).collect(Collectors.toList());
    }

    /**
     * search method
     *
     * @param searchDto {@link CommonSearchDto}
     * @return response
     */
    @Override
    public List<TeacherSearchResponse> searchTeacher(CommonSearchDto searchDto) {
        return teacherRepository.searchWithName(searchDto.getFirstName());
    }

    public TeacherViewModel convertToViewModel(Teacher teacher) {
        TeacherViewModel viewModel = new TeacherViewModel();
        viewModel.setId(teacher.getId());
        viewModel.setFirstName(teacher.getFirstName());
        viewModel.setLastName(teacher.getLastName());
        viewModel.setEmail(teacher.getEmail());
        viewModel.setPhone(teacher.getPhone());
        viewModel.setDepartmentViewModel(departmentService.findByTeacherId(teacher.getId()));
        viewModel.setSubjectViewModels(subjectService.getSubjectByDepartmentId(teacher.getDepartmentId()));
        return viewModel;
    }

    private Teacher convertToEntity(Teacher teacher, TeacherRequestDto teacherRequestDto) {
        teacher.setFirstName(CaseConverter.capitalizeFirstCharacter(teacherRequestDto.getFirstName()));
        teacher.setLastName(CaseConverter.capitalizeFirstCharacter(teacherRequestDto.getLastName()));
        teacher.setEmail(CaseConverter.uncapitalizeAllCharacter(teacherRequestDto.getEmail()));
        teacher.setPhone(teacherRequestDto.getPhone());
        if (Objects.nonNull(teacherRequestDto.getDepartmentId())) {
            teacher.setDepartment(departmentService.findById(teacherRequestDto.getDepartmentId()));
        }
        return teacher;
    }
}
