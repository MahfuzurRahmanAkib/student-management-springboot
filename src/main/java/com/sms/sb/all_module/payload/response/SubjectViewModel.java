package com.sms.sb.all_module.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectViewModel {
    private Long id;
    private String title;
    private String code;
    private Long departmentId;
    private String departmentCode;
    private String departmentName;

    public SubjectViewModel(Long id, String title, String code) {
        this.id = id;
        this.title = title;
        this.code = code;
    }
}
