package com.gas.app.service.currency;

import com.gas.app.entity.currency.StandardCurrencyEnum;
import com.gas.app.repository.currency.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService {

    private final ExchangeRateRepository currencyRateRepository;


    public Double convertToUSD(StandardCurrencyEnum currency, Double sum){

        Double exchangeRate = currencyRateRepository.getCurrentExchangeRate(currency.toString());
        return sum/exchangeRate;

    }
    public Double convertFromUSD(StandardCurrencyEnum currency, Double sum){

        Double exchangeRate = currencyRateRepository.getCurrentExchangeRate(currency.toString());
        return sum * exchangeRate;

    }
}
