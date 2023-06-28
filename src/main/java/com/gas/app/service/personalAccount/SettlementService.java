package com.gas.app.service.personalAccount;

import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.MeterReading;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.repository.personalAccount.CalculationRepository;
import com.gas.app.repository.personalAccount.MeterReadingRepository;
import com.gas.app.repository.personalAccount.PaymentRepository;
import com.gas.app.repository.personalAccount.PersonalGasAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final PersonalGasAccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final CalculationRepository calculationRepository;


    @Transactional
    @Scheduled(cron = "0 0 4 15 * ?")
    public void doMonthAccountCalculation() {

        accountRepository.findAllAccounts().forEach(
                (account) -> {

                    Double amountConsumed = calculateAmountConsumed(account);
                    Double accruedPayment = calculateAccruedPayment(account, amountConsumed);
                    Double paidPayment = calculatePaidPayment(account);
                    Double totalBalance = calculateTotalBalance(account,accruedPayment,paidPayment);

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

        List<Object[]> meterReadings = meterReadingRepository
                .getMeterReadingsForTheLastAndCurrentMonth(account.getId());

        Double meterReadingLastMonth = (Double) meterReadings.get(0)[1];
        Double meterReadingCurrentMonth;

        if (meterReadings.size() == 2) {
            meterReadingCurrentMonth = (Double) meterReadings.get(1)[1];
        } else {
            meterReadingCurrentMonth = createPenaltyMeterReading(account).getMeterReading();
        }
        return meterReadingCurrentMonth - meterReadingLastMonth;
    }

    @Transactional
    public MeterReading createPenaltyMeterReading(PersonalGasAccount account) {

        MeterReading meterReadingLastMonth = meterReadingRepository.getLastMeterReading(account.getId())
                .orElseThrow(NoSuchElementException::new);

        MeterReading meterReadingCurrentMonth = new MeterReading();
        meterReadingCurrentMonth.setMeterReading(meterReadingLastMonth.getMeterReading() + 100);
        meterReadingCurrentMonth.setPersonalGasAccount(account);

        return meterReadingRepository.save(meterReadingCurrentMonth);

    }
    public Double calculateAccruedPayment(PersonalGasAccount account, Double  amountConsumed) {
        return account.getAccountTariff().getTariff().getPrice() * amountConsumed;
    }

    @Transactional(readOnly = true)
    public Double calculatePaidPayment(PersonalGasAccount account) {
        return paymentRepository.getSumOfPaymentsForTheLastMonth(account.getId());
    }
    public Double calculateTotalBalance(PersonalGasAccount account, Double  accruedPayment, Double paidPayment) {
        return account.getBalance() + paidPayment - accruedPayment;
    }

}
