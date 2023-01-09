package com.sms.sb.all_module.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TeacherViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long departmentId;
    private String departmentCode;

    public TeacherViewModel(Long id, String firstName, String lastName, String email, String phone, Long departmentId, String departmentCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
        this.departmentCode = departmentCode;
    }
}
