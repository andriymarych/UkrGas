package com.gas.app.service.telegram;

import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.telegram.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;

    public TelegramUser findTelegramUserByUsername(String username) {
        return telegramUserRepository
                .findTelegramUserByUsername(username)
                .orElseThrow(() -> new ServiceException("User " + username +
                        " is not registered",
                        HttpStatus.NOT_FOUND));
    }
}
