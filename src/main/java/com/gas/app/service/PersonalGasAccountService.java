package com.gas.app.service;


import com.gas.app.exception.ServiceException;
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
    public PersonalGasAccount getAccountByAccountId(UserSessionDto userSessionDto, Long accountId) {

        verifyUser(userSessionDto);

        return personalGasAccountRepository
                .findAccountByAccountId(accountId)
                .orElseThrow(() -> new ServiceException("Could not find gas account by id [" + accountId + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public PersonalGasAccount getAccountByAccountNumber(String accountNumber) {

        return personalGasAccountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ServiceException("Could not find gas account by account number ["
                        + accountNumber + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public List<PersonalGasAccount> getAccountsByUser(UserSessionDto userSessionDto) {

        verifyUser(userSessionDto);

        return personalGasAccountRepository
                .findAccountsByUserId(userSessionDto.userId())
                .orElseThrow(() -> new ServiceException("Could not find gas accounts by user id [" + userSessionDto.userId() + "]", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void verifyUser(UserSessionDto userRequest) {
        authService.verifyAuth(userRequest);
    }
 /*   @Transactional
    public boolean verifyGasPersonalAccount(Long userId, Long personalGasAccountId) {
        PersonalGasAccount personalGasAccount = personalGasAccountRepository.findById(personalGasAccountId)
                .orElseThrow(() -> new ServiceException("Could not find personal gas account with id[" + personalGasAccountId +"]",HttpStatus.NOT_FOUND));
        if(personalGasAccount.getUser().getId().equals(userId)){
            return true;
        }
        throw new ServiceException("User id[" + userId +"] is not the owner" +
                " of personal gas account id[" + personalGasAccountId + "]", HttpStatus.FORBIDDEN);
    }*/
}
