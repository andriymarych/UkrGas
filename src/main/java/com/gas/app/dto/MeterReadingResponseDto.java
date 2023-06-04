package com.gas.app.dto;

import com.gas.app.entity.MeterReading;
import com.gas.app.entity.PersonalGasAccount;

import java.util.List;

public record MeterReadingResponseDto(PersonalGasAccount personalGasAccount, List<MeterReading> meterReadings) {
}
