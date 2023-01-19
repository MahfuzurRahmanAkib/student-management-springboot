package com.sms.sb.all_module.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<DepartmentViewModel> departmentViewModels = new ArrayList<>();
    private List<SubjectViewModel> subjectViewModels = new ArrayList<>();
}
