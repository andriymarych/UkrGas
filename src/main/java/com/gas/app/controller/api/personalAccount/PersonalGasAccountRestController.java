package com.gas.app.controller.api.personalAccount;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
import com.gas.app.service.security.AuthenticationService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/personal-accounts")

public class PersonalGasAccountRestController {

    private final PersonalGasAccountService personalGasAccountService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<Object> getPersonalAccounts() {

        List<PersonalGasAccount> gasAccounts = personalGasAccountService
                .getAccountsByUser(authenticationService.getCurrentUser());
        return ResponseHandler.generateResponse("OK", HttpStatus.OK, gasAccounts);
    }

    @GetMapping("/{personalAccountId}")
    public ResponseEntity<Object> getPersonalAccountById(@PathVariable Long personalAccountId) {
        PersonalGasAccount gasAccount = personalGasAccountService.getAccountByAccountId(personalAccountId);
        return ResponseHandler.generateResponse("OK", HttpStatus.OK, gasAccount);
    }

}


