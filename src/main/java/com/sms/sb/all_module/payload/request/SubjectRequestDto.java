package com.sms.sb.all_module.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequestDto {
    private Long id;
    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;
    @NotNull
    @NotBlank
    @Size(max = 100)
    private String code;
}
