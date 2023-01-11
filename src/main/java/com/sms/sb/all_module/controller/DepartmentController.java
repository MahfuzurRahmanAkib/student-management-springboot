package com.sms.sb.all_module.controller;

import com.sms.sb.all_module.entity.Department;
import com.sms.sb.all_module.payload.request.DepartmentRequestDto;
import com.sms.sb.all_module.payload.response.DepartmentViewModel;
import com.sms.sb.all_module.payload.search.CommonSearchDto;
import com.sms.sb.all_module.service.DepartmentService;
import com.sms.sb.all_module.service.impl.DepartmentServiceImpl;
import com.sms.sb.common.constant.ApplicationConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    private DepartmentService departmentService;
    private DepartmentServiceImpl departmentServiceImpl;

    public DepartmentController(DepartmentService departmentService, DepartmentServiceImpl departmentServiceImpl) {
        super();
        this.departmentService = departmentService;
        this.departmentServiceImpl = departmentServiceImpl;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        departmentService.save(departmentRequestDto);
        return new ResponseEntity<>(ApplicationConstant.SAVED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        departmentService.update(departmentRequestDto);
        return new ResponseEntity<>(ApplicationConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        departmentService.deleteById(id);
        return new ResponseEntity<>(ApplicationConstant.DELETED_SUCCESSFULLY, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<DepartmentViewModel>> getAll() {
        List<DepartmentViewModel> allDepartments = departmentService.findAll();
        return new ResponseEntity<>(allDepartments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentViewModel> getById(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        DepartmentViewModel departmentViewModel = departmentServiceImpl.convertToViewModel(department);
        return new ResponseEntity<>(departmentViewModel, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<DepartmentViewModel>> search(@Valid @RequestBody CommonSearchDto searchDto) {
        List<DepartmentViewModel> departmentViewModels = departmentService.searchDepartment(searchDto);
        return new ResponseEntity<>(departmentViewModels, HttpStatus.OK);
    }
}
