package com.gas.app.controller;


import com.gas.app.dto.UserLoginForm;
import com.gas.app.dto.UserRegistrationForm;
import com.gas.app.entity.User;
import com.gas.app.service.UserService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Object> login(@RequestBody UserLoginForm userForm) {

        User user = userService.authenticateUser(userForm.email(), userForm.password());
        return ResponseHandler.generateResponse("Login is successful", HttpStatus.OK, user);
    }

    @PostMapping("/registration")
    @Transactional
    public ResponseEntity<Object> registration(@RequestBody UserRegistrationForm userForm) {
        User user = null;
        user = userService.registerUser(userForm);
        return ResponseHandler.generateResponse("Account has been created", HttpStatus.OK, user);
    }


}