package com.gas.app.repository.personalAccount;

import com.gas.app.entity.personalAccount.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    @Query("select distinct calculation from Calculation calculation " +
            "left join fetch calculation.personalGasAccount account " +
            "left join fetch account.accountTariff accountTariff " +
            "left join fetch accountTariff.tariff tariff " +
            "where account.id = :personalAccountId")
    Optional<List<Calculation>> findCalculationsWithTariffByPersonalAccountId(Long  personalAccountId);

}