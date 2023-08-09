package com.gas.app.repository.personalAccount;

import com.gas.app.entity.personalAccount.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {


    @Query("select distinct payment from Payment payment " +
            "left join fetch payment.personalGasAccount account " +
            "where account.id = :personalGasAccountId")
    List<Payment> findAllByPersonalAccountId(Long personalGasAccountId);


    @Query(value = "select sum(amount_paid) from ukr_gas.payment where personal_gas_account_id = :personalGasAccountId " +
            "and date_trunc('month', date) = date_trunc('month', current_date - interval '1' month) ", nativeQuery = true)
    Double findSumOfPaymentsForTheLastMonthByPersonalAccountId(Long personalGasAccountId);

}
