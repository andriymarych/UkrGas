package com.gas.app.controller;


import com.gas.app.dto.UserRegistrationForm;
import com.gas.app.entity.User;
import com.gas.app.service.UserService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    @GetMapping
    public String getPage() {
        return "registration";
    }
    @PostMapping("/")
    @Transactional
    public ResponseEntity<Object> registration(@RequestBody UserRegistrationForm userForm) {
        User user = userService.registerUser(userForm);
        return ResponseHandler.generateResponse("Account has been created", HttpStatus.CREATED, user);
    }
}
