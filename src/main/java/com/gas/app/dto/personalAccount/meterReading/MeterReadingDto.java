package com.gas.app.dto.personalAccount.meterReading;

import java.sql.Date;

public record MeterReadingDto(Long id, Double meterReading, Date date, Long personalGasAccountId) {
}