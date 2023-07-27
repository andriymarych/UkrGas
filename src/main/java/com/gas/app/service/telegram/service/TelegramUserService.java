package com.gas.app.service.telegram.service;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.telegram.TelegramUserGasPersonalAccountRepository;
import com.gas.app.repository.telegram.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    private final TelegramUserGasPersonalAccountRepository telegramUserGasPersonalAccountRepository;

    public TelegramUser getTelegramUserByUsername(String username) {
        return telegramUserRepository
                .findTelegramUserByUsername(username)
                .orElseThrow(() -> new ServiceException("User " + username +
                        " is not registered",
                        HttpStatus.NOT_FOUND));
    }
   public List<PersonalGasAccount> getPersonalAccountListByUsername(String username) {
        return telegramUserRepository
                .findPersonalAccountListByUsername(username);
    }
    public boolean isUserAuthorized(String username){
        return telegramUserGasPersonalAccountRepository.findTelegramUserGasPersonalAccountByUsername(username);
    }
}
