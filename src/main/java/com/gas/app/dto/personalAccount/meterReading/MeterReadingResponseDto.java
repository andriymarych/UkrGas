package com.gas.app.dto.personalAccount.meterReading;

import com.gas.app.entity.personalAccount.MeterReading;

import java.util.List;

public record MeterReadingResponseDto(List<MeterReading> meterReadings) {
}
