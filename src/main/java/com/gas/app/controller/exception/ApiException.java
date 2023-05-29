package com.gas.app.controller.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime dateTime;

}
