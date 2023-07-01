package com.gas.app.dto.currency;

import java.util.Map;

public record ExchangeRateResponseDto(Meta meta, Map<String, ExchangeRateDto> data) {

}
