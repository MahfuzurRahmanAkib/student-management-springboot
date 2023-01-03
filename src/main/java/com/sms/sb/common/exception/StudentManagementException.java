package com.sms.sb.common.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class StudentManagementException extends RuntimeException {
    private static final long serialVersionUID = 1436995162658277359L;
    private final String errorId;
    private final String traceId;
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public StudentManagementException(String errorId, HttpStatus status, String traceId) {
        this.errorId = errorId;
        this.traceId = traceId;
        this.status = status;
    }
}
