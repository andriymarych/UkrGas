package com.gas.app.service;

import com.gas.app.dto.*;
import com.gas.app.entity.Calculation;
import com.gas.app.entity.PersonalGasAccount;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.CalculationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CalculationService {
    private final CalculationRepository calculationRepository;
    private final PersonalGasAccountService accountService;
    @Transactional
    public CalculationResponseDto getCalculationsByPersonalAccountId(UserSessionDto userSessionDto,
                                                             Long personalAccountId) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(userSessionDto, personalAccountId);

        List<Calculation> calculations = calculationRepository.
                findCalculationsWithTariffByPersonalAccountId(personalGasAccount.getId())
                .orElseThrow(
                        () -> new ServiceException("Could not find calculations with personal gas account["
                                + personalAccountId + "]", HttpStatus.NOT_FOUND));

        return new CalculationResponseDto(personalGasAccount, calculations);
    }
}

