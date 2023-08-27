package com.gas.app.controller.api.personalAccount;

import com.gas.app.dto.personalAccount.calculation.CalculationResponseDto;
import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.service.personalAccount.CalculationService;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
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
@RequestMapping("/api/v2/personal-accounts/{personalAccountId}")
public class CalculationRestController {

    private final CalculationService calculationService;
    private final PersonalGasAccountService personalGasAccountService;

    @GetMapping("/calculations")
    public ResponseEntity<Object> getCalculations(@PathVariable Long personalAccountId) {

        PersonalGasAccount personalGasAccount = personalGasAccountService
                .getAccountByAccountId(personalAccountId);

        List<Calculation> calculations = calculationService
                .getCalculationsByPersonalAccountId(personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, new CalculationResponseDto(personalGasAccount,calculations));
    }
}
