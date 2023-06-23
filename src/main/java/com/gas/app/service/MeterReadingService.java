package com.gas.app.service;


import com.gas.app.dto.MeterReadingDto;
import com.gas.app.dto.MeterReadingRequestDto;
import com.gas.app.entity.MeterReading;
import com.gas.app.exception.ServiceException;
import com.gas.app.dto.MeterReadingResponseDto;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.PersonalGasAccount;
import com.gas.app.repository.MeterReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeterReadingService {
    private final MeterReadingRepository meterReadingRepository;
    private final PersonalGasAccountService accountService;

    @Transactional
    public MeterReadingResponseDto getMeterReadingsByPersonalAccountId(UserSessionDto userSessionDto,
                                                                  Long personalAccountId) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(userSessionDto, personalAccountId);

        List<MeterReadingDto> meterReadings = meterReadingRepository.
                findMeterReadingsByPersonalAccountId(personalGasAccount.getId())
                .orElseThrow(
                        () -> new ServiceException("Could not find meterReadings with personal gas account["
                                + personalAccountId + "]", HttpStatus.NOT_FOUND));

        return new MeterReadingResponseDto(personalGasAccount, meterReadings);
    }

    @Transactional
    public MeterReading saveMeterReading(MeterReadingRequestDto requestDto) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(requestDto.userSession(), requestDto.gasAccountId());

        MeterReading meterReading = new MeterReading(requestDto.meterReading());
        meterReading.setPersonalGasAccount(personalGasAccount);
        validateMeterReading(meterReading);
        return meterReadingRepository.save(meterReading);
    }

    @Transactional
    public void validateMeterReading(MeterReading meterReading) {

        Optional<MeterReading> lastMeterReading = meterReadingRepository
                .getLastMeterReading(meterReading.getPersonalGasAccount().getId());
        if (lastMeterReading.isPresent()  && lastMeterReading.get().getMeterReading() > meterReading.getMeterReading()) {
            throw new ServiceException("The entered value of the meter reading is less than the previous one",
                    HttpStatus.CONFLICT);
        }

    }

}
