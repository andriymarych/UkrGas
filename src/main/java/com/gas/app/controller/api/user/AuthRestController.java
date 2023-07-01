package com.gas.app.controller.api.user;


import com.gas.app.dto.user.UserLoginDto;
import com.gas.app.dto.user.UserRegistrationDto;
import com.gas.app.dto.user.UserSessionDto;
import com.gas.app.entity.user.User;
import com.gas.app.service.user.UserService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> login(@RequestBody UserLoginDto userForm) {
        User user = userService.authenticateUser(userForm.email(), userForm.password());
        UserSessionDto userSessionDto = new UserSessionDto(user.getId(),user.getAuth().getId());
        return ResponseHandler.generateResponse("Login is successful", HttpStatus.OK, userSessionDto);
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@RequestBody UserRegistrationDto userForm) {
        User user = userService.registerUser(userForm);
        UserSessionDto userSessionDto = new UserSessionDto(user.getId(),user.getAuth().getId());
        return ResponseHandler.generateResponse("Account has been created", HttpStatus.CREATED, userSessionDto);
    }


}