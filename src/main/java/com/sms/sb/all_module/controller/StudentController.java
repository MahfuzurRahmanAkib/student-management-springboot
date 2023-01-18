package com.sms.sb.all_module.controller;

import com.sms.sb.all_module.entity.Student;
import com.sms.sb.all_module.payload.search.StudentSearchResponse;
import com.sms.sb.all_module.payload.request.StudentRequestDto;
import com.sms.sb.all_module.payload.response.StudentViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;
import com.sms.sb.all_module.service.StudentService;
import com.sms.sb.all_module.service.impl.StudentServiceImpl;
import com.sms.sb.common.constant.ApplicationConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
    private StudentService studentService;
    private StudentServiceImpl studentServiceimpl;

    public StudentController(StudentService studentService, StudentServiceImpl studentServiceimpl) {
        super();
        this.studentService = studentService;
        this.studentServiceimpl = studentServiceimpl;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody StudentRequestDto studentRequestDto) {
        studentService.save(studentRequestDto);
        return new ResponseEntity<>(ApplicationConstant.SAVED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody StudentRequestDto studentRequestDto) {
        studentService.update(studentRequestDto);
        return new ResponseEntity<>(ApplicationConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return new ResponseEntity<>(ApplicationConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<StudentViewModel>> getAll() {
        List<StudentViewModel> allStudents = studentService.findAll();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentViewModel> getById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        StudentViewModel studentViewModel = studentServiceimpl.convertToViewModel(student);
        return new ResponseEntity<>(studentViewModel, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<StudentSearchResponse>> search(@Valid @RequestBody CommonSearchDto searchDto) {
        List<StudentSearchResponse> studentViewModelList = studentService.searchStudent(searchDto);
        return new ResponseEntity<>(studentViewModelList, HttpStatus.OK);
    }
}
