package com.gas.app.controller.api.personalAccount;

import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.service.personalAccount.CalculationService;
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

    @GetMapping("/calculations")
    public ResponseEntity<Object> getCalculations(@PathVariable Long personalAccountId) {

        List<Calculation> calculationResponseDto = calculationService
                .getCalculationsByPersonalAccountId(personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, calculationResponseDto);
    }
}
