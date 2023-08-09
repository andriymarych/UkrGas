package com.gas.app.repository.personalAccount;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface PersonalGasAccountRepository extends JpaRepository<PersonalGasAccount, Long> {
    @Query("select distinct account from PersonalGasAccount account " +
            "left join fetch account.person " +
            "left join fetch account.address " +
            "left join fetch account.accountTariff accountTariff " +
            "left join fetch accountTariff.tariff " +
            "where account.accountNumber = :accountNumber")
    Optional<PersonalGasAccount> findByAccountNumber(String accountNumber);

    @Query("select distinct account from PersonalGasAccount account " +
            "left join fetch account.accountTariff account_tariff " +
            "left join fetch account_tariff.tariff " +
            "left join fetch account.person " +
            "left join fetch account.address " +
            "left join fetch account.user user " +
            "left join fetch account.person ")
    List<PersonalGasAccount> findAllAccounts();

    @Query("select distinct account from PersonalGasAccount account " +
            "left join fetch account.accountTariff account_tariff " +
            "left join fetch account_tariff.tariff " +
            "left join fetch account.person " +
            "left join fetch account.address " +
            "left join fetch account.person  where account.id = :personalGasAccountId")
    Optional<PersonalGasAccount> findByAccountId(Long personalGasAccountId);

    @Query("select distinct account " +
            "from PersonalGasAccount account " +
            "left join fetch account.accountTariff account_tariff " +
            "left join fetch account_tariff.tariff " +
            "left join fetch account.person " +
            "left join fetch account.address " +
            "where account.user = :user")
    List<PersonalGasAccount> findAllByUser(User user);

}