package com.sms.sb.all_module.payload.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentSearchDto {
    private Long id;
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
