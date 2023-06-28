package com.gas.app.service.fuel;

import com.gas.app.dto.fuel.FuelPriceDto;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import com.gas.app.entity.fuel.FuelPrice;
import com.gas.app.entity.fuel.StandardFuelTypeEnum;
import com.gas.app.service.currency.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class FuelPriceDtoMapper implements Function<FuelPriceDto, List<FuelPrice>> {
    private final CurrencyConversionService currencyConversionService;
    @Override
    public List<FuelPrice> apply(FuelPriceDto fuelPriceDto) {
        return List.of(
                new FuelPrice(
                        StandardFuelTypeEnum.LPG,
                        currencyConversionService.convertToUSD(
                                StandardCurrencyEnum.EUR,
                                Double.valueOf(
                                        fuelPriceDto.lpg()
                                                .replace(",", "."))
                        ),
                        fuelPriceDto.country()
                ),
                new FuelPrice(
                        StandardFuelTypeEnum.DIESEL,
                        currencyConversionService.convertToUSD(
                                StandardCurrencyEnum.EUR,
                                Double.valueOf(
                                        fuelPriceDto.diesel()
                                                .replace(",", "."))
                        ),
                        fuelPriceDto.country()
                ),
                new FuelPrice(
                        StandardFuelTypeEnum.GASOLINE,
                        currencyConversionService.convertToUSD(
                                StandardCurrencyEnum.EUR,
                                Double.valueOf(
                                        fuelPriceDto.gasoline()
                                                .replace(",", "."))
                        ),
                        fuelPriceDto.country()
                )
        );
    }

}
