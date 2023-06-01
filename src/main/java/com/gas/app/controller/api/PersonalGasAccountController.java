package com.gas.app.controller.api;

import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.PersonalGasAccount;
import com.gas.app.service.PersonalGasAccountService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/personalAccount")

public class PersonalGasAccountController {

    private final PersonalGasAccountService personalGasAccountService;


    @PostMapping("/")
    @Transactional
    public ResponseEntity<Object> getPersonalAccountsByUserId(@RequestBody UserSessionDto user) {
        List<PersonalGasAccount> gasAccounts = personalGasAccountService.getAccountsByUser(user);
        return ResponseHandler.generateResponse("Login is successful", HttpStatus.OK, gasAccounts);
    }


}

/*
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonalGasAccountController {

    private final UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Object> login(@RequestBody UserLoginDto userForm) {

        User user = userService.authenticateUser(userForm.email(), userForm.password());
        return ResponseHandler.generateResponse("Login is successful", HttpStatus.OK, user);
    }
}*/
