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
@RequestMapping("/api/v1/personalAccounts/")

public class PersonalGasAccountRestController {

    private final PersonalGasAccountService personalGasAccountService;

    @GetMapping("/")
    @Transactional
    public ResponseEntity<Object> getPersonalAccounts(@RequestParam Long userId,
                                                      @RequestParam Long authId) {
        UserSessionDto user = new UserSessionDto(userId, authId);
        List<PersonalGasAccount> gasAccounts = personalGasAccountService.getAccountsByUser(user);
        return ResponseHandler.generateResponse("Login is successful", HttpStatus.OK, gasAccounts);
    }


}


