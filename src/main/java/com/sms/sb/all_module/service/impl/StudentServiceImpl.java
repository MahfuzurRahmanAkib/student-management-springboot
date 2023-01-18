package com.sms.sb.all_module.service.impl;

import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.search.StudentSearchResponse;
import com.sms.sb.all_module.payload.request.StudentRequestDto;
import com.sms.sb.all_module.payload.response.StudentViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;
import com.sms.sb.all_module.repository.StudentRepository;
import com.sms.sb.all_module.service.DepartmentService;
import com.sms.sb.all_module.service.StudentService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    /**
     * Constructor
     *
     * @param studentRepository {@link StudentRepository}
     * @param departmentService {@link DepartmentService}
     */
    public StudentServiceImpl(StudentRepository studentRepository,
                              DepartmentService departmentService) {
        super();
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
    }

    /**
     * save method
     *
     * @param studentRequestDto {@link DepartmentService}
     * @return message
     */
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

    /**
     * update method
     *
     * @param studentRequestDto {@link StudentRequestDto}
     * @return message
     */
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

    /**
     * Delete method
     *
     * @param id required id
     */
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

    /**
     * Single get method
     *
     * @param id required id
     * @return response
     */
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

    /**
     * find all method
     *
     * @return response
     */
    @Override
    public List<StudentViewModel> findAll() {
        List<Student> studentList = studentRepository.findAllByDeletedFalse();
        return studentList.stream().map(this::convertToViewModel).collect(Collectors.toList());
    }

    /**
     * search method
     *
     * @param searchDto {@link CommonSearchDto}
     * @return response
     */
    public List<StudentSearchResponse> searchStudent(CommonSearchDto searchDto) {
        return studentRepository.searchWithName(searchDto.getFirstName());
    }

    public StudentViewModel convertToViewModel(Student student) {
        StudentViewModel viewModel = new StudentViewModel();
        viewModel.setId(student.getId());
        viewModel.setFirstName(student.getFirstName());
        viewModel.setLastName(student.getLastName());
        viewModel.setGender(student.getGender());
        viewModel.setEmail(student.getEmail());
        viewModel.setPhone(student.getPhone());
        viewModel.setCombinedViewModels(departmentService.findByStudentId(student.getId()));
        return viewModel;
    }

    private Student convertToEntity(Student student, StudentRequestDto studentRequestDto) {
        student.setFirstName(CaseConverter.capitalizeFirstCharacter(studentRequestDto.getFirstName()));
        student.setLastName(CaseConverter.capitalizeFirstCharacter(studentRequestDto.getLastName()));
        student.setEmail(CaseConverter.uncapitalizeAllCharacter(studentRequestDto.getEmail()));
        student.setPhone(studentRequestDto.getPhone());
        student.setGender(studentRequestDto.getGender());
        if (Objects.nonNull(studentRequestDto.getDepartmentId())) {
            student.setDepartment(departmentService.findById(studentRequestDto.getDepartmentId()));
        }
        return student;
    }
}
