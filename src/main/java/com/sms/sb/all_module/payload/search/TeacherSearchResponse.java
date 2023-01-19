package com.sms.sb.all_module.payload.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSearchResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String subjectTitle;
    private String subjectCode;
    private String departmentCode;
    private String departmentName;
}
