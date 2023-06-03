package com.gas.app.dto;

public record MeterReadingRequestDto(UserSessionDto userSession,
                                     Long gasAccountId, Double meterReading) {
}
