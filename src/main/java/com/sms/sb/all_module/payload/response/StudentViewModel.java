package com.sms.sb.all_module.payload.response;

import com.sms.sb.all_module.payload.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String phone;
    private DepartmentViewModel departmentViewModel;
    private List<SubjectViewModel> subjectViewModels = new ArrayList<>();
}
