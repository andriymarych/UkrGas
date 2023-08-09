package com.gas.app.service.personalAccount;


import com.gas.app.dto.personalAccount.meterReading.MeterReadingRequestDto;
import com.gas.app.entity.personalAccount.MeterReading;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.personalAccount.MeterReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "meterReadings")
@RequiredArgsConstructor
public class MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;
    private final PersonalGasAccountService accountService;

    @Transactional(readOnly = true)
    @Cacheable
    public List<MeterReading> getMeterReadingsByPersonalAccountId(Long personalAccountId) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(personalAccountId);

        return meterReadingRepository.
                findAllByPersonalGasAccountId(personalGasAccount.getId());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(key = "#personalAccountId")
    public MeterReading saveMeterReading(Long personalAccountId, MeterReadingRequestDto requestDto) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(personalAccountId);

        MeterReading meterReading = new MeterReading(requestDto.meterReading());
        meterReading.setPersonalGasAccount(personalGasAccount);

        if(isMeterReadingValid(meterReading)){
            return meterReadingRepository.save(meterReading);
        }else {
            throw new ServiceException("The entered value of the meter reading is less than the previous one",
                    HttpStatus.CONFLICT);
        }

    }
    @Transactional(readOnly = true)
    public boolean isBillingPeriodClosed(Long personalAccountId){

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(personalAccountId);

        return meterReadingRepository.isBillingPeriodClosed(personalGasAccount.getId());

    }
    @Transactional(readOnly = true)
    public boolean isMeterReadingValid(MeterReading meterReading) {

        Optional<MeterReading> lastMeterReading = meterReadingRepository
                .findLastByPersonalGasAccountId(meterReading.getPersonalGasAccount().getId());
        return lastMeterReading.isEmpty() || lastMeterReading.get().getMeterReading() <= meterReading.getMeterReading();

    }

}
