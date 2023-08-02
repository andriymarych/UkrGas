package com.gas.app.service.currency;

import com.gas.app.dto.currency.ExchangeRateDto;
import com.gas.app.entity.currency.ExchangeRate;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class ExchangeRateDtoMapper implements Function<ExchangeRateDto, ExchangeRate> {

    @Override
    public ExchangeRate apply(ExchangeRateDto exchangeRateDto) {
        return new ExchangeRate(StandardCurrencyEnum.valueOf(exchangeRateDto.code()), Double.valueOf(exchangeRateDto.value()));
    }
}
