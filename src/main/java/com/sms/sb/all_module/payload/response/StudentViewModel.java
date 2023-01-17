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
    private List<SubjectDepartmentCombinedViewModel> combinedViewModels = new ArrayList<>();

    public StudentViewModel(Long id, String firstName, String lastName, Gender gender, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }
}
