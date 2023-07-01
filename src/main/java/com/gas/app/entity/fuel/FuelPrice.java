package com.gas.app.entity.fuel;

import com.gas.app.entity.currency.StandardCurrencyEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Date;

@Entity
@Table(name = "fuel_price")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FuelPrice {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StandardFuelTypeEnum type;

    @Column(name = "price")
    private Double price;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private StandardCurrencyEnum currency = StandardCurrencyEnum.USD;

    @Column(name = "country")
    private String country;

    @Column(name = "date", insertable = false)
    @Generated(GenerationTime.INSERT)
    private Date date;

    public FuelPrice(StandardFuelTypeEnum type, Double price,String country) {
        this.type = type;
        this.price = price;
        this.country = country;
    }
}
