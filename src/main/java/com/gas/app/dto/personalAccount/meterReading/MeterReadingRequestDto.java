package com.gas.app.dto.personalAccount.meterReading;

import com.gas.app.dto.user.UserSessionDto;

public record MeterReadingRequestDto(UserSessionDto userSession,
                                     Long gasAccountId, Double meterReading) {
}
