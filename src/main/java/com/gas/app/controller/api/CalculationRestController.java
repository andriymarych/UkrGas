package com.gas.app.controller.api;

import com.gas.app.dto.CalculationResponseDto;
import com.gas.app.dto.PaymentRequestDto;
import com.gas.app.dto.PaymentResponseDto;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.Payment;
import com.gas.app.service.CalculationService;
import com.gas.app.service.PaymentService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/personal-account/")
public class CalculationRestController {

    private final CalculationService calculationService;
    @GetMapping("/{personalAccountId}/calculations/")
    @Transactional
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
