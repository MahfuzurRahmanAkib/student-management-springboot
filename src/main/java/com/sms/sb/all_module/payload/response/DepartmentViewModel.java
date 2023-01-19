package com.sms.sb.all_module.payload.response;

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
public class DepartmentViewModel {
    private Long id;
    private String code;
    private String name;
    private List<SubjectViewModel> subjectViewModels = new ArrayList<>();
    public DepartmentViewModel(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
