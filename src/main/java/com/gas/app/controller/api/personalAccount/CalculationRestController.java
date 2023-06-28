package com.gas.app.controller.api.personalAccount;

import com.gas.app.dto.personalAccount.calculation.CalculationResponseDto;
import com.gas.app.dto.user.UserSessionDto;
import com.gas.app.service.personalAccount.CalculationService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/personal-accounts/{personalAccountId}")
public class CalculationRestController {

    private final CalculationService calculationService;
    @GetMapping("/calculations")
    public ResponseEntity<Object> getCalculations(@PathVariable Long personalAccountId,
                                              @RequestParam Long userId,
                                              @RequestParam Long authId) {

        UserSessionDto userSessionDto = new UserSessionDto(userId, authId);
        CalculationResponseDto calculationResponseDto = calculationService
                .getCalculationsByPersonalAccountId(userSessionDto, personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, calculationResponseDto);
    }
}
