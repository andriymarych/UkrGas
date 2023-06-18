package com.gas.app.repository;

import com.gas.app.dto.MeterReadingDto;
import com.gas.app.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    @Query("select new com.gas.app.dto.MeterReadingDto(meterReading.id," +
            "meterReading.meterReading, meterReading.date, account.id) from MeterReading meterReading " +
            "inner join meterReading.personalGasAccount account " +
             "where account.id = :personalAccountId")
    Optional<List<MeterReadingDto>> findMeterReadingsByPersonalAccountId(Long personalAccountId);


    @Query("select meterReading from MeterReading meterReading " +
            "left join fetch meterReading.personalGasAccount account " +
            "where account.id = :personalAccountId " +
            "order by meterReading.id desc limit 1")
    Optional<MeterReading> getLastMeterReading(Long personalAccountId);


    @Query(value = "select id, meter_reading, date " +
            "from ukr_gas.meter_reading" +
            " where personal_gas_account_id = :personalAccountId " +
            "and date_trunc('month', date)  >= date_trunc('month', current_date - interval '1' month) " +
            "order by id ", nativeQuery = true)
   List<Object[]> getMeterReadingsForTheLastAndCurrentMonth(Long personalAccountId);

}
