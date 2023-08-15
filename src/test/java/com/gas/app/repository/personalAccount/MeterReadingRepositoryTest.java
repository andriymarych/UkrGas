package com.gas.app.repository.personalAccount;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.personalAccount.*;
import com.gas.app.entity.security.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class MeterReadingRepositoryTest {


    @Autowired
    private MeterReadingRepository meterReadingRepository;
    PersonalGasAccount personalGasAccount ;
    List<MeterReading> expectedMeterReadings;

    @BeforeEach
    void setUp() {
        personalGasAccount = new PersonalGasAccount();

        MeterReading meterReadingCurrentMonth = new MeterReading();
        meterReadingCurrentMonth.setMeterReading(12520d);
        meterReadingCurrentMonth.setPersonalGasAccount(personalGasAccount);

        MeterReading meterReadingPreviousMonth = new MeterReading();
        meterReadingPreviousMonth.setMeterReading(12421d);
        meterReadingPreviousMonth.setDate(Date.valueOf(LocalDate.now().minusMonths(1)));
        meterReadingPreviousMonth.setPersonalGasAccount(personalGasAccount);

        expectedMeterReadings =  List.of(meterReadingPreviousMonth, meterReadingCurrentMonth);
        meterReadingRepository.saveAll(expectedMeterReadings);
    }

    @Test
    void shouldReturnMeterReadingsByPersonalAccountId() {
        List<MeterReading> foundMeterReadings  = meterReadingRepository
                .findAllByPersonalGasAccountId(personalGasAccount.getId());

        assertThat(foundMeterReadings).hasSameElementsAs(expectedMeterReadings);
    }
    @Test
    void shouldReturnMeterReadingForTheLastMonthByAccountId(){
        MeterReading meterReadingCurrentMonth = expectedMeterReadings
                .stream()
                .filter(meterReading -> meterReading.getDate().equals(Date.valueOf(LocalDate.now())))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        Optional<MeterReading> foundMeterReading  = meterReadingRepository
                .findLastByPersonalGasAccountId(personalGasAccount.getId());

        assertThat(foundMeterReading.isPresent()).isTrue();
        assertThat(foundMeterReading.get()).isEqualTo(meterReadingCurrentMonth);
    }
    @Test
    void shouldReturnTrueWhenBillingPeriodIsClosed(){
        boolean isBillingPeriodIsClosed = meterReadingRepository
                .isBillingPeriodClosed(personalGasAccount.getId());

        assertThat(isBillingPeriodIsClosed).isTrue();
    }
    @Test
    void shouldReturnFalseWhenBillingPeriodIsOpen(){
        PersonalGasAccount personalGasAccountWithOpenBillingPeriod
                = new PersonalGasAccount();

        boolean isBillingPeriodIsClosed = meterReadingRepository
                .isBillingPeriodClosed(personalGasAccountWithOpenBillingPeriod.getId());

        assertThat(isBillingPeriodIsClosed).isFalse();
    }
    @Test
    @Transactional
    void shouldReturnUsersWithUntransmittedMeterReadingForTheCurrentMonth(){
        User userWithTransmittedMeterReading = new User();
        personalGasAccount.setUser(userWithTransmittedMeterReading);

        User userWithUntransmittedMeterReading = new User();
        PersonalGasAccount personalGasAccountWithUnransmittedMeterReading = new PersonalGasAccount();
        personalGasAccountWithUnransmittedMeterReading.setUser(userWithUntransmittedMeterReading);
        setUpPersonalGasAccountAdditionalData(personalGasAccountWithUnransmittedMeterReading);

        MeterReading meterReadingPreviousMonth = new MeterReading();
        meterReadingPreviousMonth.setPersonalGasAccount(personalGasAccountWithUnransmittedMeterReading);
        meterReadingPreviousMonth.setMeterReading(12421d);
        meterReadingPreviousMonth.setDate(Date.valueOf(LocalDate.now().minusMonths(1)));

        meterReadingRepository.save(meterReadingPreviousMonth);

        List<User> usersWithUntransmittedMeterReading = meterReadingRepository
                .findUsersWithUntransmittedMeterReadingForTheCurrentMonth();

        assertThat(usersWithUntransmittedMeterReading.size()).isEqualTo(1);
        assertThat(usersWithUntransmittedMeterReading).contains(userWithUntransmittedMeterReading);
        assertThat(usersWithUntransmittedMeterReading).doesNotContain(userWithTransmittedMeterReading);
    }

    @Test
    @Transactional
    void shouldReturnMeterReadingByMonth(){
        MeterReading meterReadingPreviousMonth = new MeterReading();
        meterReadingPreviousMonth.setMeterReading(12520d);
        meterReadingPreviousMonth.setDate(Date.valueOf(LocalDate.now().minusMonths(2)));
        meterReadingPreviousMonth.setPersonalGasAccount(personalGasAccount);

        meterReadingRepository.save(meterReadingPreviousMonth);

        Optional<MeterReading> foundMeterReading = meterReadingRepository
                .findByPersonalGasAccountByIdAndMonth(personalGasAccount.getId(),
                        Date.valueOf(LocalDate.now().minusMonths(2)));

        assertThat(foundMeterReading.isPresent()).isTrue();
        assertThat(foundMeterReading.get()).isEqualTo(meterReadingPreviousMonth);
    }

    void setUpPersonalGasAccountAdditionalData(PersonalGasAccount personalGasAccount){
        Address address = new Address();
        Person person = new Person();
        Tariff tariff = new Tariff();
        AccountTariff accountTariff = new AccountTariff();
        accountTariff.setTariff(tariff);

        personalGasAccount.setAddress(address);
        personalGasAccount.setPerson(person);
        personalGasAccount.setAccountTariff(accountTariff);
    }
}