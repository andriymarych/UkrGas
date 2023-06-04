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


    @Query(value = "select sum(amount_paid) from ukr_gas.payment where personal_gas_account_id = :personalAccountId " +
            "and date_trunc('month', date) = date_trunc('month', current_date - interval '1' month) ", nativeQuery = true)
    Double getSumOfPaymentsForTheLastMonth(Long personalAccountId);

}
