package com.gas.app.dto.fuel;

import java.util.List;

public record FuelPriceResponseDto(List<FuelPriceDto> results,String success) {
}
