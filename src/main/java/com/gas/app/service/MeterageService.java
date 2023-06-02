package com.gas.app.service;


import com.gas.app.exception.ServiceException;
import com.gas.app.dto.MeterageResponseDto;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.Meterage;
import com.gas.app.entity.PersonalGasAccount;
import com.gas.app.repository.MeterageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeterageService {
    private final MeterageRepository meterageRepository;
    private final PersonalGasAccountService accountService;

    @Transactional
    public MeterageResponseDto getMeterageByPersonalAccountId(UserSessionDto userSessionDto,
                                                              Long personalAccountId) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(userSessionDto, personalAccountId);

        List<Meterage> meterages = meterageRepository.
                findMeteragesByPersonalAccountId(personalGasAccount.getId())
                .orElseThrow(
                        () -> new ServiceException("Could not find meterages with personal gas account["
                                + personalAccountId + "]", HttpStatus.NOT_FOUND));

        return new MeterageResponseDto(personalGasAccount, meterages);
    }


}
