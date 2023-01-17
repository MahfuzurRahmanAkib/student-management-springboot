package com.sms.sb.all_module.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDepartmentCombinedViewModel {
    private Long id;
    private String subjectTitle;
    private String subjectCode;
    private Long departmentId;
    private String departmentCode;
    private String departmentName;
}
