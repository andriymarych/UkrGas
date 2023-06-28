package com.gas.app.dto.fuel;

public record FuelPriceChangeDto(String fuelType, Double price, Double priceChange, Double priceChangePct) {
}
