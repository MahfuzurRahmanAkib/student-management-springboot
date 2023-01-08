package com.sms.sb.all_module.payload.request;

import com.sms.sb.common.constant.ApplicationConstant;
import com.sms.sb.common.constant.ErrorId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequestDto {
    private Long id;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String firstName;
    @Size(max = 100)
    private String lastName;
    @NotBlank
    @NotNull
    @Size(max = 250)
    @Email(regexp = ApplicationConstant.EMAIL_VALIDATION_REGEX, message = ErrorId.INVALID_EMAIL_PATTERN)
    private String email;
    @NotBlank
    @NotNull
    @Size(max = 16)
    private String phone;
    private String subject;
}