package com.sms.sb.all_module.payload.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSearchResponse {
    private Long id;
    private String subjectTitle;
    private String subjectCode;
    private String departmentCode;
    private String departmentName;
}
