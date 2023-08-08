package com.gas.app.entity.currency;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

import java.sql.Date;

@Entity
@Table(name = "exchange_rate")
@Data
@NoArgsConstructor
public class ExchangeRate {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "currency_from")
    @Enumerated(EnumType.STRING)
    private StandardCurrencyEnum currencyFrom = StandardCurrencyEnum.USD;

    @Column(name = "currency_to")
    @Enumerated(EnumType.STRING)
    private StandardCurrencyEnum currencyTo;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @Column(name = "date", insertable = false)
    @Generated
    private Date date;

    public ExchangeRate(StandardCurrencyEnum currencyTo, Double exchangeRate) {
        this.currencyTo = currencyTo;
        this.exchangeRate = exchangeRate;
    }
}
