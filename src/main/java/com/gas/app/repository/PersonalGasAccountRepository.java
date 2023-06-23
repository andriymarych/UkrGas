package com.gas.app.repository;

import com.gas.app.entity.PersonalGasAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface PersonalGasAccountRepository extends JpaRepository<PersonalGasAccount, Long> {
    @Query("select distinct account from PersonalGasAccount account " +
            "left join fetch account.person  where account.accountNumber = :accountNumber")
    Optional<PersonalGasAccount> findAccountByAccountNumber(String accountNumber);

    @Query("select distinct account from PersonalGasAccount account " +
            "left join fetch account.accountTariff account_tariff " +
            "left join fetch account_tariff.tariff " +
            "left join fetch account.person " +
            "left join fetch account.address " +
            "left join fetch account.user user " +
            "left join fetch user.auth " +
            "left join fetch account.person ")
    List<PersonalGasAccount> findAllAccounts();

    @Query("select distinct account from PersonalGasAccount account " +
            "left join fetch account.accountTariff account_tariff " +
            "left join fetch account_tariff.tariff " +
            "left join fetch account.person " +
            "left join fetch account.address " +
            "left join fetch account.person  where account.id = :accountId")
    Optional<PersonalGasAccount> findAccountByAccountId(Long accountId);

    @Query("select distinct account " +
            "from PersonalGasAccount account " +
            "left join fetch account.accountTariff account_tariff " +
            "left join fetch account_tariff.tariff " +
            "left join fetch account.person " +
            "left join fetch account.address " +
            "where account.user.id = :userId")
    Optional<List<PersonalGasAccount>> findAccountsByUserId(Long userId);

}