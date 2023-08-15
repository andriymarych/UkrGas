package com.gas.app.repository.personalAccount;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.security.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class PersonalGasAccountRepositoryTest {

    @Autowired
    private PersonalGasAccountRepository personalGasAccountRepository;

    @Test
    @Transactional
    void shouldReturnAccountByAccountNumber() {
        PersonalGasAccount personalGasAccount = new PersonalGasAccount();
        personalGasAccount.setAccountNumber("03434543");

        personalGasAccountRepository.save(personalGasAccount);

        String accountNumber = personalGasAccount.getAccountNumber();
        Optional<PersonalGasAccount> foundPersonalGasAccount = personalGasAccountRepository
                .findByAccountNumber(accountNumber);

        assertThat(foundPersonalGasAccount.get()).isEqualTo(personalGasAccount);

    }


    @Test
    @Transactional
    void shouldReturnPersonalGasAccountByAccountId() {
        PersonalGasAccount personalGasAccount = new PersonalGasAccount();

        personalGasAccountRepository.save(personalGasAccount);

        Long personalGasAccountId = personalGasAccount.getId();
        Optional<PersonalGasAccount> foundPersonalGasAccount = personalGasAccountRepository
                .findByAccountId(personalGasAccountId);

        assertThat(foundPersonalGasAccount.get()).isEqualTo(personalGasAccount);

    }

    @Test
    @Transactional
    void shouldReturnPersonalGasAccountsByUser() {
        User user = new User();

        PersonalGasAccount firstPersonalGasAccount = new PersonalGasAccount();
        firstPersonalGasAccount.setUser(user);
        PersonalGasAccount secondPersonalGasAccount = new PersonalGasAccount();
        secondPersonalGasAccount.setUser(user);

        List<PersonalGasAccount> expectedPersonalGasAccounts =
                List.of(firstPersonalGasAccount,secondPersonalGasAccount);

        personalGasAccountRepository.saveAll(expectedPersonalGasAccounts);

        List<PersonalGasAccount> foundPersonalGasAccounts = personalGasAccountRepository
                .findAllByUser(user);

        assertThat(foundPersonalGasAccounts).hasSameElementsAs(expectedPersonalGasAccounts);

    }
}