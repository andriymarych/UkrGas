package com.gas.app.entity.fuel;

import com.gas.app.entity.currency.StandardCurrencyEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;

import java.util.Date;

@Entity
@Table(name = "fuel_price")
@Data
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
    @Generated
    private Date date;

    public FuelPrice(StandardFuelTypeEnum type, Double price,String country) {
        this.type = type;
        this.price = price;
        this.country = country;
    }
}
