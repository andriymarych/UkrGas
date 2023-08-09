package com.gas.app.service.fuel;

import com.gas.app.dto.fuel.FuelPriceChangeDto;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import com.gas.app.entity.fuel.FuelPrice;
import com.gas.app.repository.fuel.FuelPriceRepository;
import com.gas.app.service.currency.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelPriceService {


    private final FuelPriceRepository fuelPriceRepository;
    private final CurrencyConversionService conversionService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Cacheable("fuelPrices")
    public List<FuelPriceChangeDto> getFuelPrices(StandardCurrencyEnum currency) {

        List<FuelPrice> fuelPricesPreviousDay = fuelPriceRepository
                .findByDate(Date.valueOf(LocalDate.now().minusDays(1)));
        List<FuelPrice> fuelPricesCurrentDay = fuelPriceRepository
                .findByDate(Date.valueOf(LocalDate.now()));

        fuelPricesPreviousDay = convertCurrencyOfFuelPrices(fuelPricesPreviousDay, currency);
        fuelPricesCurrentDay = convertCurrencyOfFuelPrices(fuelPricesCurrentDay, currency);

        return getFuelPriceChangeDtoList(fuelPricesPreviousDay,fuelPricesCurrentDay);
    }

    public List<FuelPrice> convertCurrencyOfFuelPrices(List<FuelPrice> fuelPrices, StandardCurrencyEnum currency) {

        fuelPrices.forEach(
                fuelPrice -> {
                    fuelPrice.setPrice(conversionService.convertFromUSD(currency, fuelPrice.getPrice()));
                    fuelPrice.setCurrency(currency);
                }
        );

        return fuelPrices;
    }

    private List<FuelPriceChangeDto> getFuelPriceChangeDtoList(List<FuelPrice> fuelPricesPreviousDay, List<FuelPrice> fuelPricesCurrentDay) {

        fuelPricesPreviousDay.sort(Comparator.comparing(FuelPrice::getType));
        fuelPricesCurrentDay.sort(Comparator.comparing(FuelPrice::getType));

        List<FuelPriceChangeDto> fuelPriceChangeDtoList = new ArrayList<>();

        for (int i = 0; i < fuelPricesCurrentDay.size(); i++) {
            fuelPriceChangeDtoList.add(getFuelPriceChangeDto(fuelPricesPreviousDay.get(i), fuelPricesCurrentDay.get(i)));
        }
        return fuelPriceChangeDtoList;
    }

    private FuelPriceChangeDto getFuelPriceChangeDto(FuelPrice fuelPricePreviousDay, FuelPrice fuelPriceCurrentDay) {

        Double previousPriceValue = fuelPricePreviousDay.getPrice();
        Double currentPriceValue = fuelPriceCurrentDay.getPrice();
        Double priceChange = currentPriceValue - previousPriceValue;
        Double priceChangePct = (currentPriceValue - previousPriceValue) / previousPriceValue * 100;
        return new FuelPriceChangeDto(fuelPriceCurrentDay.getType().toString(), currentPriceValue, priceChange, priceChangePct);
    }

}
