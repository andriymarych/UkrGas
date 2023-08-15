package com.gas.app.repository.telegram;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccount;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccountKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TelegramUserPersonalGasAccountRepository extends JpaRepository<TelegramUserGasPersonalAccount,
        TelegramUserGasPersonalAccountKey> {

    @Query("select distinct telegramUserPersonalGasAccount " +
            "from TelegramUserGasPersonalAccount  telegramUserPersonalGasAccount " +
            "where telegramUserPersonalGasAccount.key.telegramUser = :telegramUser " +
            "and telegramUserPersonalGasAccount.verified = false")
    Optional<TelegramUserGasPersonalAccount> findUnverifiedTelegramUserGasPersonalAccountByTelegramUser(TelegramUser telegramUser);

    @Query("select distinct personalGasAccount.key.personalGasAccount " +
            "from TelegramUserGasPersonalAccount personalGasAccount  " +
            "where personalGasAccount.key.telegramUser.username = :username " +
            "and personalGasAccount.verified = true")
    List<PersonalGasAccount> findPersonalGasAccountListByTelegramUsername(String username);

}
