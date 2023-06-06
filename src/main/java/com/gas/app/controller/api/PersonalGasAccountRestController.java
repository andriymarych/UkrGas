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
@RequestMapping("/api/v1/personal-account/")

public class PersonalGasAccountRestController {

    private final PersonalGasAccountService personalGasAccountService;


    @GetMapping("/{personalAccountId}")
    @Transactional
    public ResponseEntity<Object> getPersonalAccountById(@PathVariable Long personalAccountId,
                                                             @RequestParam Long userId,
                                                      @RequestParam Long authId) {
        UserSessionDto user = new UserSessionDto(userId, authId);
        PersonalGasAccount gasAccount = personalGasAccountService.getAccountByAccountId(user,personalAccountId);
        return ResponseHandler.generateResponse("OK", HttpStatus.OK, gasAccount);
    }

    @GetMapping("/all")
    @Transactional
    public ResponseEntity<Object> getPersonalAccounts(@RequestParam Long userId,
                                                      @RequestParam Long authId) {
        UserSessionDto user = new UserSessionDto(userId, authId);
        List<PersonalGasAccount> gasAccounts = personalGasAccountService.getAccountsByUser(user);
        return ResponseHandler.generateResponse("OK", HttpStatus.OK, gasAccounts);
    }


}


