package com.gas.app.repository;

import com.gas.app.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeterageRepository extends JpaRepository<MeterReading, Long> {

    @Query("select distinct meterReading from MeterReading meterReading " +
            "left join fetch meterReading.personalGasAccount account " +
             "where account.id = :personalAccountId")
    Optional<List<MeterReading>> findMeterReadingsByPersonalAccountId(Long personalAccountId);


    @Query("select meterReading from MeterReading meterReading " +
            "left join fetch meterReading.personalGasAccount account " +
            "where account.id = :personalAccountId " +
            "order by meterReading.id desc limit 1")
    Optional<MeterReading> getLastMeterReading(Long personalAccountId);


}
