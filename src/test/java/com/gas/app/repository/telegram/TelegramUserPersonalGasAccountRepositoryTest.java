package com.gas.app.repository.telegram;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccount;
import com.gas.app.entity.telegram.TelegramUserGasPersonalAccountKey;
import com.gas.app.repository.personalAccount.PersonalGasAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class TelegramUserPersonalGasAccountRepositoryTest {

    @Autowired
    private TelegramUserPersonalGasAccountRepository telegramUserPersonalGasAccountRepository;
    @Autowired
    private PersonalGasAccountRepository personalGasAccountRepository;
    @Autowired
    private TelegramUserRepository telegramUserRepository;

    private TelegramUser telegramUser;
    private List<TelegramUserGasPersonalAccount> telegramUserGasPersonalAccountList;

    @BeforeEach
    void setUp() {
        telegramUser = new TelegramUser();
        telegramUser.setUsername("jamesclarkson");
        telegramUserRepository.save(telegramUser);

        PersonalGasAccount personalGasAccount1 = new PersonalGasAccount();
        personalGasAccountRepository.save(personalGasAccount1);

        PersonalGasAccount personalGasAccount2 = new PersonalGasAccount();
        personalGasAccountRepository.save(personalGasAccount2);

        TelegramUserGasPersonalAccountKey telegramUserGasPersonalAccountKey1 = new TelegramUserGasPersonalAccountKey();
        telegramUserGasPersonalAccountKey1.setTelegramUser(telegramUser);
        telegramUserGasPersonalAccountKey1.setPersonalGasAccount(personalGasAccount1);

        TelegramUserGasPersonalAccountKey telegramUserGasPersonalAccountKey2 = new TelegramUserGasPersonalAccountKey();
        telegramUserGasPersonalAccountKey2.setTelegramUser(telegramUser);
        telegramUserGasPersonalAccountKey2.setPersonalGasAccount(personalGasAccount2);

        TelegramUserGasPersonalAccount telegramUserGasPersonalAccount1 =
                new TelegramUserGasPersonalAccount(telegramUserGasPersonalAccountKey1);
        telegramUserGasPersonalAccount1.setVerified(true);

        TelegramUserGasPersonalAccount telegramUserGasPersonalAccount2 =
                new TelegramUserGasPersonalAccount(telegramUserGasPersonalAccountKey2);
        telegramUserGasPersonalAccount2.setVerified(true);

        telegramUserGasPersonalAccountList =
                List.of(telegramUserGasPersonalAccount1, telegramUserGasPersonalAccount2);

        telegramUserPersonalGasAccountRepository.saveAll(telegramUserGasPersonalAccountList);
    }

    @Test
    @Transactional
    void shouldReturnUnverifiedGasPersonalAccountByTelegramUser() {
        TelegramUserGasPersonalAccount telegramUserGasPersonalAccount = telegramUserGasPersonalAccountList.get(0);
        telegramUserGasPersonalAccount.setVerified(false);
        telegramUserPersonalGasAccountRepository.save(telegramUserGasPersonalAccount);

        TelegramUserGasPersonalAccount foundUnverifiedTelegramUserGasPersonalAccount =
                telegramUserPersonalGasAccountRepository
                        .findUnverifiedTelegramUserGasPersonalAccountByTelegramUser(telegramUser)
                        .orElseThrow(NoSuchElementException::new);

        assertThat(foundUnverifiedTelegramUserGasPersonalAccount).isEqualTo(telegramUserGasPersonalAccount);
    }

    @Test
    @Transactional
    void shouldReturnGasPersonalAccountListByTelegramUsername() {
        List<PersonalGasAccount> foundTelegramUserGasPersonalAccountList =
                telegramUserPersonalGasAccountRepository.findPersonalGasAccountListByTelegramUsername(telegramUser.getUsername());

        assertThat(foundTelegramUserGasPersonalAccountList).isEqualTo(telegramUserGasPersonalAccountList
                .stream()
                .map(TelegramUserGasPersonalAccount::getKey)
                .map(TelegramUserGasPersonalAccountKey::getPersonalGasAccount)
                .toList());
    }

    @Test
    @Transactional
    void shouldReturnPersonalAccountListByUsername(){
        List<PersonalGasAccount> foundTelegramUserGasPersonalAccountList =
                telegramUserPersonalGasAccountRepository
                        .findPersonalGasAccountListByTelegramUsername(telegramUser.getUsername());

        assertThat(foundTelegramUserGasPersonalAccountList).hasSameElementsAs(foundTelegramUserGasPersonalAccountList);
    }
}