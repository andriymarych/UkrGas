package com.gas.app.service.fuel;

import com.gas.app.dto.fuel.FuelPriceDto;
import com.gas.app.dto.fuel.FuelPriceResponseDto;
import com.gas.app.entity.fuel.FuelPrice;
import com.gas.app.repository.fuel.FuelPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuelPriceUpdateService {
    private final String SERVICE_URL = "https://api.collectapi.com/gasPrice/europeanCountries";

    @Value("${gasPrice.api.key}")
    private String apiKey;

    private final FuelPriceRepository fuelPriceRepository;
    private final FuelPriceDtoMapper fuelPriceDtoMapper;

    //Fuel prices are updated every day at 00:02
    @Transactional
    @CacheEvict(value = "fuelPrices", allEntries = true)
    @Scheduled(cron = "0 4 0 * * ?")
    public void fuelPriceDailyUpdate() {
        List<FuelPrice> fuelPrices = getFuelPriceListFromApi()
                .stream()
                .filter(fuelPriceDto -> fuelPriceDto.country().equals("Ukraine"))
                .map(fuelPriceDtoMapper)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        fuelPriceRepository.saveAll(fuelPrices);
    }

    private List<FuelPriceDto> getFuelPriceListFromApi() {
        return WebClient.create(SERVICE_URL)
                .get()
                .header("authorization", apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FuelPriceResponseDto.class)
                .map(FuelPriceResponseDto::results)
                .share()
                .block();
    }
}
