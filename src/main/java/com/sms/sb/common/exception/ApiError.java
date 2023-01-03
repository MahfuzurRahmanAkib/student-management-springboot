package com.sms.sb.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {
    List<StudentManagementError> apiErrors;
    public void addError(StudentManagementError error) {
        if(apiErrors == null) {
            apiErrors = new ArrayList<>();
        }
        apiErrors.add(error);
    }
}
