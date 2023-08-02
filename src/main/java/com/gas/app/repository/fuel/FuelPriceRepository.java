package com.gas.app.repository.fuel;

import com.gas.app.entity.fuel.FuelPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FuelPriceRepository extends JpaRepository<FuelPrice, Long> {

    @Query("select fuelPrice from FuelPrice fuelPrice " +
            "where fuelPrice.date = :date")
    List<FuelPrice> getFuelPriceByDate(Date date);

}