package com.gas.app.service.currency;

import com.gas.app.dto.currency.ExchangeRateDto;
import com.gas.app.dto.currency.ExchangeRateResponseDto;
import com.gas.app.entity.currency.ExchangeRate;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.currency.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ExchangeRateUpdateService {
    private final String SERVICE_URL = "https://api.currencyapi.com/v3";

    @Value("${currencyRate.api.key}")
    private String apiKey;

    private final ExchangeRateRepository currencyRateRepository;
    private final ExchangeRateDtoMapper currencyDtoMapper;

    Predicate<ExchangeRateDto> isCurrencyStandard = exchangeRate ->
            StandardCurrencyEnum
                    .toList()
                    .contains(exchangeRate.code());

    //Exchange rates are updated every day at 00:00
    @Transactional
    @Scheduled(cron = "0 3 0 * * ?")
    public void currencyRateDailyUpdate() {

        List<ExchangeRate> currencyRates = getCurrencyRateListFromApi()
                .stream()
                .filter(isCurrencyStandard)
                .map(currencyDtoMapper)
                .toList();

        currencyRateRepository.saveAll(currencyRates);

    }

    private List<ExchangeRateDto> getCurrencyRateListFromApi() {
        Map<String, ExchangeRateDto> responseResultMap = Optional.ofNullable(
                WebClient.create(SERVICE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder.path("/latest")
                        .queryParam("apikey", apiKey)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ExchangeRateResponseDto.class)
                .map(ExchangeRateResponseDto::data)
                .share()
                .block()
                )
                .orElseThrow(() ->
                        new ServiceException("API service api.currencyapi.com is unavailable",
                                HttpStatus.SERVICE_UNAVAILABLE));

        return responseResultMap
                .values()
                .stream()
                .toList();
    }

}
