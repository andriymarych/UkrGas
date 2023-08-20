package com.gas.app.dto.personalAccount.meterReading;

import com.gas.app.entity.personalAccount.PersonalGasAccount;

import java.util.List;

public record MeterReadingResponseDto(PersonalGasAccount personalGasAccount, List<MeterReadingDto> meterReadings) {
}
