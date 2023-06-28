package com.gas.app.dto.fuel;

public record FuelPriceDto(String currency, String lpg, String diesel,
                           String gasoline, String country) {
}
