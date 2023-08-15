package com.gas.app.repository.currency;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.currency.ExchangeRate;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    @Transactional
    void shouldReturnCurrentExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate(StandardCurrencyEnum.EUR, 0.91);
        exchangeRateRepository.save(exchangeRate);

        Double expectedExchangeRateValue = exchangeRate.getExchangeRate();
        Double foundExchangeRateValue = exchangeRateRepository.findForTheCurrentDayByCurrency(StandardCurrencyEnum.EUR);

        assertThat(expectedExchangeRateValue).isEqualTo(foundExchangeRateValue);
    }
}