package com.gas.app.controller.api.personalAccount;

import com.gas.app.dto.personalAccount.meterReading.MeterReadingDto;
import com.gas.app.dto.personalAccount.meterReading.MeterReadingRequestDto;
import com.gas.app.dto.personalAccount.meterReading.MeterReadingResponseDto;
import com.gas.app.entity.personalAccount.MeterReading;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.service.personalAccount.MeterReadingService;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/personal-accounts/{personalAccountId}")
public class MeterReadingRestController {

    private final PersonalGasAccountService personalGasAccountService;
    private final MeterReadingService meterReadingService;
    @GetMapping("/meter-readings")
    public ResponseEntity<Object> getMeterReadings(@PathVariable Long personalAccountId) {

        PersonalGasAccount personalGasAccount = personalGasAccountService
                .getAccountByAccountId(personalAccountId);

        List<MeterReadingDto> meterReadingDtoList = meterReadingService
                .getMeterReadingsByPersonalAccountId(personalAccountId)
                .stream()
                .map(meterReading ->
                        new MeterReadingDto(
                                meterReading.getId(),
                                meterReading.getMeterReading(),
                                meterReading.getDate(),
                                meterReading.getPersonalGasAccount().getId()))
                .toList();

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, new MeterReadingResponseDto(personalGasAccount,meterReadingDtoList));
    }
    @PostMapping("/meter-readings")
    public ResponseEntity<Object> saveMeterReading(@PathVariable Long personalAccountId,
                                                   @RequestBody MeterReadingRequestDto requestDto) {

        MeterReading meterReading = meterReadingService
                .saveMeterReading(personalAccountId, requestDto);

        return ResponseHandler
                .generateResponse("Meter reading ["
                                + meterReading.getMeterReading() +
                                "] has been successfully saved",
                HttpStatus.CREATED, meterReading);
    }
}
