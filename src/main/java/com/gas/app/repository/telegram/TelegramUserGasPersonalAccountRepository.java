package com.gas.app.repository.telegram;

import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccount;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccountKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TelegramUserGasPersonalAccountRepository extends JpaRepository<TelegramUserGasPersonalAccount,
        TelegramUserGasPersonalAccountKey> {

    @Query("select distinct telegramUserPersonalGasAccount " +
            "from TelegramUserGasPersonalAccount  telegramUserPersonalGasAccount " +
            "where telegramUserPersonalGasAccount.key.telegramUser = :telegramUser " +
            "and telegramUserPersonalGasAccount.verified = false")
    Optional<TelegramUserGasPersonalAccount> findTelegramUserGasPersonalAccountByUserId(TelegramUser telegramUser);


}
