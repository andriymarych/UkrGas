package com.gas.app.service.personalAccount;

import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.repository.personalAccount.CalculationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CalculationService {
    private final CalculationRepository calculationRepository;
    private final PersonalGasAccountService accountService;
    @Transactional(readOnly = true)
    @Cacheable("calculations")
    public List<Calculation> getCalculationsByPersonalAccountId(Long personalAccountId) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(personalAccountId);

        return  calculationRepository.
                findAllByPersonalGasAccountId(personalGasAccount.getId());
    }
}

