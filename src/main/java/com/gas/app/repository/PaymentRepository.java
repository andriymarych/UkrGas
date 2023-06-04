package com.gas.app.repository;

import com.gas.app.entity.MeterReading;
import com.gas.app.entity.Payment;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {


    @Query("select distinct payment from Payment payment " +
            "left join fetch payment.personalGasAccount account " +
            "where account.id = :personalAccountId")
    Optional<List<Payment>> findPaymentsByPersonalAccountId(Long personalAccountId);


    @Query("select meterReading from MeterReading meterReading " +
            "left join fetch meterReading.personalGasAccount account " +
            "where account.id = :personalAccountId " +
            "order by meterReading.id desc limit 1")
    Optional<MeterReading> getLastMeterReading(Long personalAccountId);
}
