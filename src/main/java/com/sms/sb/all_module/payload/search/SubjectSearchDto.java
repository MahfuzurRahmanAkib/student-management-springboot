package com.sms.sb.all_module.payload.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectSearchDto {
    private Long id;
    private String title;
    private String code;
}
