package com.gas.app.service.fuel;

import com.gas.app.dto.fuel.FuelPriceChangeDto;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import com.gas.app.entity.fuel.FuelPrice;
import com.gas.app.repository.fuel.FuelPriceRepository;
import com.gas.app.service.currency.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuelPriceService {


    private final FuelPriceRepository fuelPriceRepository;
    private final CurrencyConversionService conversionService;

    @Transactional(readOnly = true)
    public List<FuelPriceChangeDto> getFuelPrices(StandardCurrencyEnum currency) {

        List<FuelPrice> fuelPrices = fuelPriceRepository.getFuelPriceForTheLastTwoDays();
        fuelPrices.forEach(
                fuelPrice -> {
                    fuelPrice.setPrice(conversionService.convertFromUSD(currency,fuelPrice.getPrice()));
                    fuelPrice.setCurrency(currency);
                }

        );
        return getFuelPriceChangeDtoList(fuelPrices);
    }

    private List<FuelPriceChangeDto> getFuelPriceChangeDtoList(List<FuelPrice> fuelPrices) {

        Map<String, List<FuelPrice>> groupedFuelPriceMap =
                fuelPrices.stream().collect(Collectors.groupingBy(fuelPrice -> fuelPrice.getType().toString()));
        List<FuelPriceChangeDto> fuelPriceChangeDtoList = new ArrayList<>();

        for (var entry : groupedFuelPriceMap.entrySet()) {

            List<FuelPrice> fuelPriceForTheLastTwoDaysList = entry.getValue();
            fuelPriceChangeDtoList.add(getFuelPriceChangeDto(fuelPriceForTheLastTwoDaysList));

        }
        return fuelPriceChangeDtoList;

    }

    private FuelPriceChangeDto getFuelPriceChangeDto(List<FuelPrice> fuelPriceForTheLastTwoDaysList) {

        FuelPrice newFuelPrice = fuelPriceForTheLastTwoDaysList.get(0);
        FuelPrice oldFuelPrice = fuelPriceForTheLastTwoDaysList.get(1);
        Double newPriceValue = newFuelPrice.getPrice();
        Double priceChange = newPriceValue- oldFuelPrice.getPrice() ;
        Double priceChangePct = (newFuelPrice.getPrice() - oldFuelPrice.getPrice()) / oldFuelPrice.getPrice() * 100;
        return new FuelPriceChangeDto(newFuelPrice.getType().toString(), newFuelPrice.getPrice(), priceChange, priceChangePct);

    }


}
