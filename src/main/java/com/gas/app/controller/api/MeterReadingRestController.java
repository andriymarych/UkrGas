package com.gas.app.controller.api;

import com.gas.app.dto.MeterReadingRequestDto;
import com.gas.app.dto.MeterReadingResponseDto;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.MeterReading;
import com.gas.app.service.MeterageService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/personal-accounts/{personalAccountId}")
public class MeterReadingRestController {

    private final MeterageService meterageService;
    @GetMapping("/meter-readings")
    @Transactional
    public ResponseEntity<Object> getMeterReadings(@PathVariable Long personalAccountId,
                                               @RequestParam Long userId,
                                               @RequestParam Long authId) {

        UserSessionDto userSessionDto = new UserSessionDto(userId, authId);
        MeterReadingResponseDto meterReadingResponseDto = meterageService
                .getMeterReadingsByPersonalAccountId(userSessionDto, personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, meterReadingResponseDto);
    }
    @PostMapping("/meter-readings")
    @Transactional
    public ResponseEntity<Object> saveMeterReading(@PathVariable Long personalAccountId,
                                                   @RequestBody MeterReadingRequestDto requestDto) {

        MeterReading meterReading = meterageService
                .saveMeterReading(requestDto);

        return ResponseHandler
                .generateResponse("Meter reading ["
                                + meterReading.getMeterReading() +
                                "] has been successfully saved",
                HttpStatus.OK, meterReading);
    }
}
