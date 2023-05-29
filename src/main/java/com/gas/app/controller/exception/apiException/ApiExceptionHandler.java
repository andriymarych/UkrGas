package com.gas.app.controller.exception.apiException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
