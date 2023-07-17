package com.gas.app.service.telegram.authentication;


import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccount;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccountKey;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.telegram.TelegramUserGasPersonalAccountRepository;
import com.gas.app.repository.telegram.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TelegramAuthenticationService {

    private final TelegramUserRepository telegramUserRepository;
    private final TelegramUserGasPersonalAccountRepository telegramUserGasPersonalAccountRepository;

    @Transactional
    public PersonalGasAccount authenticateGasAccount(TelegramUser user, Long gasMeterNumber) {

        TelegramUserGasPersonalAccount  telegramUserGasPersonalAccount= telegramUserGasPersonalAccountRepository
                .findTelegramUserGasPersonalAccountByUserId(user)
                .orElseThrow(()-> new ServiceException("Unverified personal gas account of Telegram user "
                        + user.getUsername() +
                        " is not found",
                        HttpStatus.NOT_FOUND));

        PersonalGasAccount personalGasAccount = telegramUserGasPersonalAccount.getKey().getPersonalGasAccount();

        if (personalGasAccount.getGasMeterNumber().equals(gasMeterNumber)) {
            telegramUserGasPersonalAccount.setVerified(true);
            telegramUserGasPersonalAccountRepository.save(telegramUserGasPersonalAccount);
            return personalGasAccount;
        }
        return null;
    }

    public TelegramUser getUserByUsername(String username){
        return  telegramUserRepository
                .findTelegramUserByUsername(username)
                .orElseThrow(()-> new ServiceException("Telegram user with username " + username +
                        " is not found",
                        HttpStatus.NOT_FOUND));
    }
    public void addPersonalGasAccount(TelegramUser user, PersonalGasAccount personalGasAccount){
        TelegramUserGasPersonalAccount telegramUserGasPersonalAccount = new TelegramUserGasPersonalAccount();
        TelegramUserGasPersonalAccountKey key = new TelegramUserGasPersonalAccountKey();
        key.setTelegramUser(user);
        key.setPersonalGasAccount(personalGasAccount);
        telegramUserGasPersonalAccount.setKey(key);
        telegramUserGasPersonalAccountRepository.save(telegramUserGasPersonalAccount);

    }
}
