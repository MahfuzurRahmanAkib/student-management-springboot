package com.sms.sb.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentManagementError {
    private String code;
    private String message;

    public StudentManagementError(String code, String message) {
        this.message = message;
        this.code = code;
    }
}
