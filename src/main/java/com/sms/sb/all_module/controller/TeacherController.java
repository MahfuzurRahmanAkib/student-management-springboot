package com.sms.sb.all_module.controller;

import com.sms.sb.all_module.entity.Teacher;
import com.sms.sb.all_module.payload.request.TeacherRequestDto;
import com.sms.sb.all_module.payload.response.TeacherViewModel;
import com.sms.sb.all_module.payload.search.TeacherSearchDto;
import com.sms.sb.all_module.service.TeacherService;
import com.sms.sb.all_module.service.impl.TeacherServiceImpl;
import com.sms.sb.common.constant.ApplicationConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {
    private TeacherService teacherService;
    private TeacherServiceImpl teacherServiceImpl;

    public TeacherController(TeacherService teacherService, TeacherServiceImpl teacherServiceImpl) {
        super();
        this.teacherService = teacherService;
        this.teacherServiceImpl = teacherServiceImpl;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody TeacherRequestDto teacherRequestDto) {
        teacherService.save(teacherRequestDto);
        return new ResponseEntity<>(ApplicationConstant.SAVED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody TeacherRequestDto teacherRequestDto) {
        teacherService.update(teacherRequestDto);
        return new ResponseEntity<>(ApplicationConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        teacherService.deleteById(id);
        return new ResponseEntity<>(ApplicationConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TeacherViewModel>> getAll() {
        List<TeacherViewModel> allStudents = teacherService.findAll();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherViewModel> getById(@PathVariable Long id) {
        Teacher teacher = teacherService.findById(id);
        TeacherViewModel teacherViewModel = teacherServiceImpl.convertToViewModel(teacher);
        return new ResponseEntity<>(teacherViewModel, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<TeacherViewModel>> search(@Valid @RequestBody TeacherSearchDto searchDto) {
        List<TeacherViewModel> teacherViewModelList = teacherService.searchTeacher(searchDto);
        return new ResponseEntity<>(teacherViewModelList, HttpStatus.OK);
    }
}
