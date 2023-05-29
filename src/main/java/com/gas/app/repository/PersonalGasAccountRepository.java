package com.gas.app.repository;

import com.gas.app.entity.PersonalGasAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PersonalGasAccountRepository extends JpaRepository<PersonalGasAccount, Long> {
    @Query("select distinct account from PersonalGasAccount account " +
            "left join fetch account.person  where account.accountNumber = :accountNumber")
    Optional<PersonalGasAccount> findGasAccountByAccountNumber(String accountNumber);



}