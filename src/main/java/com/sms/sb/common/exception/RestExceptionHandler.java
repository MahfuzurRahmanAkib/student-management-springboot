package com.sms.sb.common.exception;

import com.sms.sb.common.constant.ApplicationConstant;
import com.sms.sb.common.constant.ErrorId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ApiError apiError = new ApiError();
        StudentManagementError studentManagementError = new StudentManagementError(ErrorId.SYSTEM_ERROR, exception.getLocalizedMessage());
        apiError.addError(studentManagementError);
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object>
    handleConstraintViolationExceptionAllException(ConstraintViolationException exception, WebRequest request) {
        ApiError apiError = new ApiError();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        violations.forEach(violation -> {
            StudentManagementError studentManagementError = getReservationError(violation.getMessageTemplate());
            apiError.addError(studentManagementError);
        });
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentManagementException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(StudentManagementException exception, WebRequest request) {
        ApiError apiError = new ApiError();
        StudentManagementError studentManagementError = getReservationError(exception.getErrorId());
        apiError.addError(studentManagementError);
        return new ResponseEntity(apiError, exception.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StudentManagementException reservationServerException = (StudentManagementException) exception.getMostSpecificCause();
        ApiError apiError = new ApiError();
        StudentManagementError error = getReservationError(reservationServerException.getErrorId());
        apiError.addError(error);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            StudentManagementError studentManagementError = getReservationError(error.getDefaultMessage());
            apiError.addError(studentManagementError);
        }
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    public StudentManagementError getReservationError(String code) {
        StudentManagementError studentManagementError = ErrorCodeReader.getReservationError(code);
        if (studentManagementError == null) {
            return new StudentManagementError(ErrorId.SYSTEM_ERROR, ApplicationConstant.SYSTEM_ERROR_MSG);
        }
        return ErrorCodeReader.getReservationError(code);
    }

}

