package com.sms.sb.all_module.payload.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSearchDto {
    private Long id;
    private String code;
    private String name;
}
