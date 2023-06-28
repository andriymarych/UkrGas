package com.gas.app.repository.currency;

import com.gas.app.entity.currency.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    @Query("select exchangeRate.exchangeRate " +
            "from ExchangeRate exchangeRate " +
            "where exchangeRate.date = current_date " +
            "and exchangeRate.currencyTo = :currency")
    Double getCurrentExchangeRate(String currency);
}
