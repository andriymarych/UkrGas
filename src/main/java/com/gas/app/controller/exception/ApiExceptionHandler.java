package com.gas.app.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<Object> handleApiRequestException(ServiceException exception) {

        ApiException apiException = new ApiException(
                exception.getMessage(),
                exception.getHttpStatus(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, exception.getHttpStatus());

    }
}
