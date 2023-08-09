package com.gas.app.service.personalAccount;


import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.security.user.User;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.personalAccount.PersonalGasAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalGasAccountService {

    private final PersonalGasAccountRepository personalGasAccountRepository;

    @Transactional(readOnly = true)
    public PersonalGasAccount getAccountByAccountId(Long accountId) {

        return personalGasAccountRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new ServiceException("Could not find gas account by id ["
                        + accountId + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true, noRollbackFor = ServiceException.class)
    public PersonalGasAccount getAccountByAccountNumber(String accountNumber) {

        return personalGasAccountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ServiceException("Could not find gas account by account number ["
                        + accountNumber + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<PersonalGasAccount> getAccountsByUser(User user) {

        return personalGasAccountRepository
                .findAllByUser(user);
    }

}
