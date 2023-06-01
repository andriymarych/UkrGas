package com.gas.app.service;


import com.gas.app.controller.exception.ServiceException;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.PersonalGasAccount;
import com.gas.app.repository.PersonalGasAccountRepository;
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

    @Transactional
    public PersonalGasAccount getAccountByAccountNumber(String accountNumber) {
        return personalGasAccountRepository
                .findGasAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ServiceException("Could not find gas account by id [" + accountNumber + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public List<PersonalGasAccount> getAccountsByUser(UserSessionDto userRequest) {

        if (!authService.verifyAuth(userRequest.userId(), userRequest.authId())) {
            return null;
        }

        return personalGasAccountRepository
                .findGasAccountsByUserId(userRequest.userId())
                .orElseThrow(() -> new ServiceException("Could not find gas accounts by user id [" + userRequest.userId() + "]", HttpStatus.NOT_FOUND));
    }
}
