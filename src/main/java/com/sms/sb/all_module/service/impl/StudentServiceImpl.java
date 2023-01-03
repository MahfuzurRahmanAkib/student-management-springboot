package com.sms.sb.all_module.service.impl;

import com.sms.sb.all_module.converter.StudentConverter;
import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.request.StudentRequestDto;
import com.sms.sb.all_module.payload.response.StudentViewModel;
import com.sms.sb.all_module.payload.search.StudentSearchDto;
import com.sms.sb.all_module.repository.StudentRepository;
import com.sms.sb.all_module.service.StudentService;
import com.sms.sb.common.constant.ApplicationConstant;
import com.sms.sb.common.constant.ErrorId;
import com.sms.sb.common.exception.StudentManagementException;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentViewModel save(StudentRequestDto studentRequestDto) {
        Student student = new Student();
        StudentConverter.convertToEntity(student, studentRequestDto);
        Student savedStudent;
        try {
            savedStudent = studentRepository.save(student);
        } catch (Exception exception) {
            LOGGER.error("Data not saved : {}", studentRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_SAVED, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        }
        return StudentConverter.convertToViewModel(savedStudent);
    }

    public Student update(StudentRequestDto studentRequestDto) {
        if (Objects.isNull(studentRequestDto.getId())) {
            LOGGER.error("Student ID is null: {}", studentRequestDto);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_NOT_FOUND, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID));
        }
        Student savedStudent = findById(studentRequestDto.getId());
        try {
            return studentRepository.save(StudentConverter.convertToEntity(savedStudent, studentRequestDto));
        } catch (Exception e) {
            LOGGER.error("Student information not updated : {}", studentRequestDto);
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
            LOGGER.error("Student not deleted with id: {}", id);
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
            LOGGER.error("Student does not exists with id: {}", id);
            throw new StudentManagementException(
                    ErrorId.INFORMATION_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND, MDC.get(ApplicationConstant.TRACE_ID));
        });
    }

    @Override
    public List<StudentViewModel> findAll() {
        List<Student> studentList = studentRepository.findAllByDeletedFalse();
        return studentList.stream().map(StudentConverter::convertToViewModel).collect(Collectors.toList());
    }

    public List<StudentViewModel> searchStudent(StudentSearchDto studentSearchDto) {
        return studentRepository.searchWithName(studentSearchDto.getFirstName());
    }
}
