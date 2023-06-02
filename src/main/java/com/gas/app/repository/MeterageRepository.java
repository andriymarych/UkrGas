package com.gas.app.repository;

import com.gas.app.entity.Meterage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeterageRepository extends JpaRepository<Meterage, Long> {

    @Query("select distinct meterage from Meterage meterage " +
            "left join fetch meterage.personalGasAccount account " +
             "where account.id = :personalAccountId")
    Optional<List<Meterage>> findMeteragesByPersonalAccountId(Long personalAccountId);
}
