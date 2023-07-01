package com.gas.app.repository.fuel;

import com.gas.app.entity.fuel.FuelPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FuelPriceRepository extends JpaRepository<FuelPrice, Long> {

    @Query(value = "select id, type, price, " +
            "currency, country, date " +
            "from ukr_gas.fuel_price " +
            "where date >= date_trunc('day', current_date - interval '1' day)" ,nativeQuery = true)
    List<FuelPrice> getFuelPriceForTheLastTwoDays();

}