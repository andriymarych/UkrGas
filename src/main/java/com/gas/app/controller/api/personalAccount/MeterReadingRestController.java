package com.gas.app.controller.api.personalAccount;

import com.gas.app.dto.personalAccount.meterReading.MeterReadingRequestDto;
import com.gas.app.dto.personalAccount.meterReading.MeterReadingResponseDto;
import com.gas.app.dto.user.UserSessionDto;
import com.gas.app.entity.personalAccount.MeterReading;
import com.gas.app.service.personalAccount.MeterReadingService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/personal-accounts/{personalAccountId}")
public class MeterReadingRestController {

    private final MeterReadingService meterReadingService;
    @GetMapping("/meter-readings")
    public ResponseEntity<Object> getMeterReadings(@PathVariable Long personalAccountId,
                                               @RequestParam Long userId,
                                               @RequestParam Long authId) {

        UserSessionDto userSessionDto = new UserSessionDto(userId, authId);
        MeterReadingResponseDto meterReadingResponseDto = meterReadingService
                .getMeterReadingsByPersonalAccountId(userSessionDto, personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, meterReadingResponseDto);
    }
    @PostMapping("/meter-readings")
    public ResponseEntity<Object> saveMeterReading(@PathVariable Long personalAccountId,
                                                   @RequestBody MeterReadingRequestDto requestDto) {

        MeterReading meterReading = meterReadingService
                .saveMeterReading(requestDto);

        return ResponseHandler
                .generateResponse("Meter reading ["
                                + meterReading.getMeterReading() +
                                "] has been successfully saved",
                HttpStatus.OK, meterReading);
    }
}
