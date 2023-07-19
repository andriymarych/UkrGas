package com.gas.app.dto.personalAccount.meterReading;

import java.sql.Date;

public record MeterReadingDto(Long id, Double value, Date date, Long personalGasAccountId) {
}