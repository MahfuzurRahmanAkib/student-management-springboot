package com.sms.sb.all_module.payload.request;

import com.sms.sb.all_module.entity.BaseEntity;
import com.sms.sb.common.constant.ApplicationConstant;
import com.sms.sb.common.constant.ErrorId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDto{
    private Long id;
    @NotBlank
    private String firstName;
    private String lastName;
    @NotBlank
    @Email(regexp = ApplicationConstant.EMAIL_VALIDATION_REGEX, message = ErrorId.INVALID_EMAIL_PATTERN)
    private String email;
    @NotBlank
    private String phone;
    private String subject;
}
