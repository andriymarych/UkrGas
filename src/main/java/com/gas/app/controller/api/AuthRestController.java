package com.gas.app.controller.api;


import com.gas.app.dto.UserLoginDto;
import com.gas.app.dto.UserRegistrationDto;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.User;
import com.gas.app.service.UserService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Object> login(@RequestBody UserLoginDto userForm) {
        User user = userService.authenticateUser(userForm.email(), userForm.password());
        UserSessionDto userSessionDto = new UserSessionDto(user.getId(),user.getAuth().getId());
        return ResponseHandler.generateResponse("Login is successful", HttpStatus.OK, userSessionDto);
    }

    @PostMapping("/registration")
    @Transactional
    public ResponseEntity<Object> registration(@RequestBody UserRegistrationDto userForm) {
        User user = userService.registerUser(userForm);
        UserSessionDto userSessionDto = new UserSessionDto(user.getId(),user.getAuth().getId());
        return ResponseHandler.generateResponse("Account has been created", HttpStatus.CREATED, userSessionDto);
    }


}