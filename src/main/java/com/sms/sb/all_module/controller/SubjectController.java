package com.sms.sb.all_module.controller;

import com.sms.sb.all_module.entity.Subject;
import com.sms.sb.all_module.payload.request.SubjectRequestDto;
import com.sms.sb.all_module.payload.response.SubjectDepartmentCombinedViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;
import com.sms.sb.all_module.service.SubjectService;
import com.sms.sb.all_module.service.impl.SubjectServiceImpl;
import com.sms.sb.common.constant.ApplicationConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/subject")
public class SubjectController {
    private SubjectService subjectService;
    private SubjectServiceImpl subjectServiceImpl;

    public SubjectController(SubjectService subjectService, SubjectServiceImpl subjectServiceImpl) {
        super();
        this.subjectService = subjectService;
        this.subjectServiceImpl = subjectServiceImpl;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody SubjectRequestDto subjectRequestDto) {
        subjectService.save(subjectRequestDto);
        return new ResponseEntity<>(ApplicationConstant.SAVED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody SubjectRequestDto subjectRequestDto) {
        subjectService.update(subjectRequestDto);
        return new ResponseEntity<>(ApplicationConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        subjectService.deleteById(id);
        return new ResponseEntity<>(ApplicationConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SubjectDepartmentCombinedViewModel>> getAll() {
        List<SubjectDepartmentCombinedViewModel> allSubjects = subjectService.findAll();
        return new ResponseEntity<>(allSubjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDepartmentCombinedViewModel> getById(@PathVariable Long id) {
        Subject subject = subjectService.findById(id);
        SubjectDepartmentCombinedViewModel subjectDepartmentCombinedViewModel = subjectServiceImpl.convertToViewModel(subject);
        return new ResponseEntity<>(subjectDepartmentCombinedViewModel, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SubjectDepartmentCombinedViewModel>> search(@Valid @RequestBody CommonSearchDto searchDto) {
        List<SubjectDepartmentCombinedViewModel> subjectDepartmentCombinedViewModels = subjectService.searchSubject(searchDto);
        return new ResponseEntity<>(subjectDepartmentCombinedViewModels, HttpStatus.OK);
    }
}
