package com.gas.app.repository.telegram;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {




    Optional<TelegramUser> findTelegramUserByUsername(String username);
    @Query("select distinct personalGasAccount.key.personalGasAccount " +
        "from TelegramUserGasPersonalAccount personalGasAccount  " +
        "where personalGasAccount.key.telegramUser.username = :username " +
        "and personalGasAccount.verified = true")
    List<PersonalGasAccount> findPersonalAccountListByUsername(String username);

}
