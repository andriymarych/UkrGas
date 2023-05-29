package com.gas.app.service;



import com.gas.app.controller.exception.ServiceException;
import com.gas.app.entity.PersonalGasAccount;
import com.gas.app.repository.PersonalGasAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonalGasAccountService {

    private final PersonalGasAccountRepository personalGasAccountRepository;

    @Transactional
    public PersonalGasAccount getAccountByAccountNumber(String accountNumber){
        return personalGasAccountRepository
                .findGasAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ServiceException("Could not find gas account by id [" + accountNumber + "]", HttpStatus.NOT_FOUND));
    }
}
