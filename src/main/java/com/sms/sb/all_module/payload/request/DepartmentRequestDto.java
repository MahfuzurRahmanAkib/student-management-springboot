package com.sms.sb.all_module.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestDto {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 100)
    private String code;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
}
