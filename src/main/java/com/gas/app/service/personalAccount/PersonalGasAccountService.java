package com.gas.app.service.personalAccount;


import com.gas.app.exception.ServiceException;
import com.gas.app.dto.user.UserSessionDto;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.repository.personalAccount.PersonalGasAccountRepository;
import com.gas.app.service.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalGasAccountService {

    private final PersonalGasAccountRepository personalGasAccountRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public PersonalGasAccount getAccountByAccountId(UserSessionDto userSessionDto, Long accountId) {

        verifyUser(userSessionDto);

        return personalGasAccountRepository
                .findAccountByAccountId(accountId)
                .orElseThrow(() -> new ServiceException("Could not find gas account by id [" + accountId + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public PersonalGasAccount getAccountByAccountNumber(String accountNumber) {

        return personalGasAccountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ServiceException("Could not find gas account by account number ["
                        + accountNumber + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<PersonalGasAccount> getAccountsByUser(UserSessionDto userSessionDto) {

        verifyUser(userSessionDto);

        return personalGasAccountRepository
                .findAccountsByUserId(userSessionDto.userId())
                .orElseThrow(() -> new ServiceException("Could not find gas accounts by user id [" + userSessionDto.userId() + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public void verifyUser(UserSessionDto userRequest) {
        authService.verifyAuth(userRequest);
    }

}
