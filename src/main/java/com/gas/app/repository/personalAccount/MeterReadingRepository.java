package com.gas.app.repository.personalAccount;

import com.gas.app.entity.personalAccount.MeterReading;
import com.gas.app.entity.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    @Query("select meterReading from MeterReading meterReading " +
            "left join fetch meterReading.personalGasAccount account " +
            "where account.id = :personalGasAccountId " +
            "order by meterReading.id DESC ")
    List<MeterReading> findAllByPersonalGasAccountId(Long personalGasAccountId);


    @Query("select meterReading from MeterReading meterReading " +
            "left join fetch meterReading.personalGasAccount account " +
            "where account.id = :personalGasAccountId " +
            "order by meterReading.id desc limit 1")
    Optional<MeterReading> findLastByPersonalGasAccountId(Long personalGasAccountId);
    @Query("select count(*) > 0 " +
            "from MeterReading  meterReading " +
            "where meterReading.personalGasAccount.id = :personalGasAccountId " +
            "and month(meterReading.date) = month(current_date)")
    Boolean isBillingPeriodClosed(Long personalGasAccountId);
    @Query("select distinct user from MeterReading meterReading " +
            "join meterReading.personalGasAccount account " +
            "left join  account.address " +
            "left join  account.person " +
            "left join  account.accountTariff accountTarrif " +
            "left join  accountTarrif.tariff " +
            "join account.user user " +
            "where account not in (select distinct account from MeterReading meterReading " +
            "inner join meterReading.personalGasAccount account " +
            "where month(meterReading.date) = month(current_date))" )
    List<User> findUsersWithUntransmittedMeterReadingForTheCurrentMonth();


    @Query("select meterReading from MeterReading meterReading " +
            "left join fetch meterReading.personalGasAccount account " +
            "where account.id = :personalGasAccountId and " +
            "month(meterReading.date) = month(CAST(:date AS timestamp))")
    Optional<MeterReading> findByPersonalGasAccountIdAndMonth(Long personalGasAccountId, Date date);

}
