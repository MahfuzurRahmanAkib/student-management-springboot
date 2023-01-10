package com.sms.sb.all_module.service.impl;

import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.request.StudentRequestDto;
import com.sms.sb.all_module.payload.response.StudentViewModel;
import com.sms.sb.all_module.payload.search.StudentSearchDto;
import com.sms.sb.all_module.repository.StudentRepository;
import com.sms.sb.all_module.service.DepartmentService;
import com.sms.sb.all_module.service.StudentService;
import com.sms.sb.all_module.service.SubjectService;
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
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private DepartmentService departmentService;
    private SubjectService subjectService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository,
                              DepartmentService departmentService,
                              SubjectService subjectService) {
        super();
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
        this.subjectService = subjectService;
    }

    @Override
    public StudentViewModel save(StudentRequestDto studentRequestDto) {
        Student student = new Student();
        convertToEntity(student, studentRequestDto);
        Student savedStudent;
        try {
            savedStudent = studentRepository.save(student);
        } catch (Exception exception) {
            LOGGER.error("Data not saved : {}", studentRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_SAVED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return convertToViewModel(savedStudent);
    }

    public Student update(StudentRequestDto studentRequestDto) {
        if (Objects.isNull(studentRequestDto.getId())) {
            LOGGER.error("ID is null: {}", studentRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_FOUND, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        Student savedStudent = findById(studentRequestDto.getId());
        try {
            return studentRepository.save(convertToEntity(savedStudent, studentRequestDto));
        } catch (Exception e) {
            LOGGER.error("Information not updated : {}", studentRequestDto);
            if (e instanceof StudentManagementException) {
                throw e;
            }
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_UPDATED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    @Override
    public void deleteById(Long id) {
        Student student = findById(id);
        student.setDeleted(Boolean.TRUE);
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            LOGGER.error("Information not deleted with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.FAIL_TO_DELETE, HttpStatus.INTERNAL_SERVER_ERROR, MDC.get(ApplicationConstant.TRACE_ID));
        }
    }

    @Override
    public Student findById(Long id) {
        if (Objects.isNull(id)) {
            LOGGER.error("Id is required !!");
            throw new StudentManagementException(
                    ErrorId.ID_IS_REQUIRED, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return studentRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> {
            LOGGER.error("Information does not exists with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        });
    }

    @Override
    public List<StudentViewModel> findAll() {
        List<Student> studentList = studentRepository.findAllByDeletedFalse();
        return studentList.stream().map(this::convertToViewModel).collect(Collectors.toList());
    }

    public StudentViewModel convertToViewModel(Student student) {
        StudentViewModel viewModel = new StudentViewModel();
        viewModel.setId(student.getId());
        viewModel.setFirstName(student.getFirstName());
        viewModel.setLastName(student.getLastName());
        viewModel.setEmail(student.getEmail());
        viewModel.setPhone(student.getPhone());
        viewModel.setSubjectViewModelList(subjectService.getSubjectInformationByStudentId(student.getId()));
        return viewModel;
    }

    private Student convertToEntity(Student student, StudentRequestDto studentRequestDto) {
        student.setFirstName(CaseConverter.capitalizeFirstCharacter(studentRequestDto.getFirstName()));
        student.setLastName(CaseConverter.capitalizeFirstCharacter(studentRequestDto.getLastName()));
        student.setEmail(CaseConverter.uncapitalizeAllCharacter(studentRequestDto.getEmail()));
        student.setPhone(studentRequestDto.getPhone());
        if (Objects.nonNull(studentRequestDto.getDepartmentId())) {
            student.setDepartment(departmentService.findById(studentRequestDto.getDepartmentId()));
        }
        return student;
    }

    public List<StudentViewModel> searchStudent(StudentSearchDto studentSearchDto) {
        return studentRepository.searchWithName(studentSearchDto.getFirstName());
    }
}
