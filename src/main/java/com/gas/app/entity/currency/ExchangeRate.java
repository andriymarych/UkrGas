package com.gas.app.entity.currency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Date;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
@ToString
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
    private String currencyTo;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @Column(name = "date", insertable = false)
    @Generated(GenerationTime.INSERT)
    private Date date;

    public ExchangeRate(String currencyTo, Double exchangeRate) {
        this.currencyTo = currencyTo;
        this.exchangeRate = exchangeRate;
    }
}
