package com.gas.app.repository.personalAccount;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.personalAccount.Tariff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class CalculationRepositoryTest {

    @Autowired
    private CalculationRepository calculationRepository;
    private PersonalGasAccount personalGasAccount;
    private List<Calculation> calculations;

    @BeforeEach
    void setUp() {
        personalGasAccount = new PersonalGasAccount();
        Tariff tariff = new Tariff();
        tariff.setPlan("Оптимальний");
        tariff.setPrice(7.2364);

        Calculation firstCalculation = new Calculation();
        firstCalculation.setPersonalGasAccount(personalGasAccount);
        firstCalculation.setBalance(300.50);
        firstCalculation.setTariff(tariff);

        Calculation secondCalculation = new Calculation();
        secondCalculation.setPersonalGasAccount(personalGasAccount);
        secondCalculation.setBalance(260.50);
        secondCalculation.setTariff(tariff);

        calculations = List.of(firstCalculation, secondCalculation);

        calculationRepository.saveAll(calculations);
    }

    @Test
    @Transactional
    void shouldReturnCalculationsByPersonalAccountId() {
        List<Calculation> foundCalculations = calculationRepository
                .findAllByPersonalGasAccountId(personalGasAccount.getId());

        assertThat(foundCalculations).hasSameElementsAs(calculations);
    }
}