package com.gas.app.service.personalAccount;

import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.MeterReading;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.personalAccount.CalculationRepository;
import com.gas.app.repository.personalAccount.MeterReadingRepository;
import com.gas.app.repository.personalAccount.PaymentRepository;
import com.gas.app.repository.personalAccount.PersonalGasAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final PersonalGasAccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final CalculationRepository calculationRepository;


    @Transactional
    @Scheduled(cron = "0 0 4 15 * ?")
    @CacheEvict(value = "calculations", allEntries = true)
    public void doMonthAccountCalculation() {

        accountRepository.findAllAccounts().forEach(
                (account) -> {

                    Double amountConsumed = calculateAmountConsumed(account);
                    Double accruedPayment = calculateAccruedPayment(account, amountConsumed);
                    Double paidPayment = calculatePaidPayment(account);
                    Double totalBalance = calculateTotalBalance(account,accruedPayment,paidPayment);
                    //TODO Builder
                    Calculation calculation = new Calculation();
                    calculation.setPersonalGasAccount(account);
                    calculation.setAmountConsumed(amountConsumed);
                    calculation.setAccruedPayment(accruedPayment);
                    calculation.setPaidPayment(paidPayment);
                    calculation.setBalance(totalBalance);
                    calculation.setTariff(account.getAccountTariff().getTariff());
                    account.setBalance(totalBalance);
                    calculationRepository.save(calculation);

                }
        );

    }

    @Transactional(readOnly = true)
    public Double calculateAmountConsumed(PersonalGasAccount account) {

        MeterReading meterReadingPreviousMonth = meterReadingRepository.
                findByPersonalGasAccountByIdAndMonth(
                        account.getId(),
                        Date.valueOf(LocalDate.now().minusMonths(1)))
                .orElseThrow(
                        () -> new ServiceException("Could not find meter reading for the last month " +
                                "with personal gas account id [" + account.getId() + "]", HttpStatus.NOT_FOUND));

        Optional<MeterReading> meterReadingCurrentMonth = meterReadingRepository.
                findByPersonalGasAccountByIdAndMonth(
                        account.getId(),
                        Date.valueOf(LocalDate.now()));

        if(meterReadingCurrentMonth.isEmpty()){
            return createPenaltyMeterReading(account).getMeterReading() - meterReadingPreviousMonth.getMeterReading();
        }
        return meterReadingCurrentMonth.get().getMeterReading() - meterReadingPreviousMonth.getMeterReading();

    }

    @Transactional
    public MeterReading createPenaltyMeterReading(PersonalGasAccount account) {

        MeterReading meterReadingPreviousMonth = meterReadingRepository.findLastByPersonalGasAccountId(account.getId())
                .orElseThrow(
                        () -> new ServiceException("Could not find meter reading for the last month " +
                                "with personal gas account id [" + account.getId() + "]", HttpStatus.NOT_FOUND));

        MeterReading meterReadingCurrentMonth = new MeterReading();
        meterReadingCurrentMonth.setMeterReading(meterReadingPreviousMonth.getMeterReading() + 100);
        meterReadingCurrentMonth.setPersonalGasAccount(account);

        return meterReadingRepository.save(meterReadingCurrentMonth);

    }
    public Double calculateAccruedPayment(PersonalGasAccount account, Double  amountConsumed) {
        return account.getAccountTariff().getTariff().getPrice() * amountConsumed;
    }

    @Transactional(readOnly = true)
    public Double calculatePaidPayment(PersonalGasAccount account) {
        return paymentRepository.findSumOfPaymentsForTheLastMonthByPersonalAccountId(account.getId());
    }
    public Double calculateTotalBalance(PersonalGasAccount account, Double  accruedPayment, Double paidPayment) {
        return account.getBalance() + paidPayment - accruedPayment;
    }

}
