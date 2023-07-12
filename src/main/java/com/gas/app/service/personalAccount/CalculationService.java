package com.gas.app.service.personalAccount;

import com.gas.app.dto.personalAccount.calculation.CalculationResponseDto;
import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.personalAccount.CalculationRepository;
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
    @Transactional(readOnly = true)
    public CalculationResponseDto getCalculationsByPersonalAccountId(Long personalAccountId) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(personalAccountId);

        List<Calculation> calculations = calculationRepository.
                findCalculationsWithTariffByPersonalAccountId(personalGasAccount.getId())
                .orElseThrow(
                        () -> new ServiceException("Could not find calculations with personal gas account["
                                + personalAccountId + "]", HttpStatus.NOT_FOUND));

        return new CalculationResponseDto(personalGasAccount, calculations);
    }
}

