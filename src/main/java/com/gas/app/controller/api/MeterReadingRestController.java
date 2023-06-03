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
@RequestMapping("/api/v1/personalAccount/")
public class MeterReadingRestController {

    private final MeterageService meterageService;
    @GetMapping("/{personalAccountId}/meter-readings/")
    @Transactional
    public ResponseEntity<Object> getMeterages(@PathVariable Long personalAccountId,
                                               @RequestParam Long userId,
                                               @RequestParam Long authId) {

        UserSessionDto userSessionDto = new UserSessionDto(userId, authId);
        MeterReadingResponseDto meterReadingResponseDto = meterageService
                .getMeterageByPersonalAccountId(userSessionDto, personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, meterReadingResponseDto);
    }
    @PostMapping("/meter-reading/")
    @Transactional
    public ResponseEntity<Object> saveMeterReading(@RequestBody MeterReadingRequestDto requestDto) {

        MeterReading meterReadingResponseDto = meterageService
                .saveMeterReading(requestDto);

        return ResponseHandler
                .generateResponse("Meter reading ["
                                + meterReadingResponseDto.getMeterReading() +
                                "] has been successfully saved",
                HttpStatus.OK, meterReadingResponseDto);
    }
}
