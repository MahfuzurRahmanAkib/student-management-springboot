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
    private List<SubjectDepartmentCombinedViewModel> combinedViewModels = new ArrayList<>();

    public TeacherViewModel(Long id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
