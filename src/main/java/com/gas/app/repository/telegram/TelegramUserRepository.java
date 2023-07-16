package com.gas.app.repository.telegram;

import com.gas.app.entity.telegram.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {


    boolean existsTelegramUserByUsername(String username);

    Optional<TelegramUser> findTelegramUserByUsername(String username);
}
