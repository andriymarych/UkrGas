package com.gas.app.controller.api;

import com.gas.app.dto.UserRegistrationDto;
import com.gas.app.entity.User;
import com.gas.app.service.UserService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationRestController {
    private final UserService userService;

    @PostMapping("/")
    @Transactional
    public ResponseEntity<Object> registration(@RequestBody UserRegistrationDto userForm) {
        User user = userService.registerUser(userForm);
        return ResponseHandler.generateResponse("Account has been created", HttpStatus.CREATED, user);
    }
}