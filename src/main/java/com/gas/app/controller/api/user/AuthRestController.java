package com.gas.app.controller.api.user;


import com.gas.app.dto.security.AuthenticationRequest;
import com.gas.app.dto.security.AuthenticationResponse;
import com.gas.app.dto.security.RegisterRequest;
import com.gas.app.service.security.AuthenticationService;
import com.gas.app.util.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseHandler.generateResponse("Login is successful", HttpStatus.OK, response);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseHandler.generateResponse("Account has been created", HttpStatus.CREATED, response);
    }
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}