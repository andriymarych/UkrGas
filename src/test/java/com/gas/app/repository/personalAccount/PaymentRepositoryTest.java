package com.gas.app.repository.personalAccount;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.personalAccount.Payment;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;
    private PersonalGasAccount personalGasAccount;
    List<Payment> payments;

    @BeforeEach
    @Transactional
    void setUp() {
        personalGasAccount = new PersonalGasAccount();
        Payment payment1 = new Payment();
        payment1.setPersonalGasAccount(personalGasAccount);
        payment1.setAmountPaid(355.50);

        Payment payment2 = new Payment();
        payment2.setPersonalGasAccount(personalGasAccount);
        payment2.setAmountPaid(500.00);

        payments = List.of(payment1,payment2);
        paymentRepository.saveAll(payments);
    }

    @Test
    @Transactional
    void shouldReturnPaymentsByPersonalAccountId() {
        List<Payment> foundPayments = paymentRepository.findAllByPersonalAccountId(personalGasAccount.getId());

        assertThat(foundPayments.isEmpty()).isFalse();
        assertThat(foundPayments).hasSameElementsAs(payments);

    }
    @Test
    @Transactional
    void shouldReturnSumOfPaymentsForTheLastMonth(){
        payments.forEach(payment -> payment.setTimestamp(Timestamp.valueOf(LocalDateTime.now().minusMonths(1))));
        Double expectedSumOfPayments = payments.stream().mapToDouble(Payment::getAmountPaid).sum();

        Double foundSumOfPayments = paymentRepository.findSumOfPaymentsForTheLastMonthByPersonalAccountId(personalGasAccount.getId());

        assertThat(foundSumOfPayments).isEqualTo(expectedSumOfPayments);
    }
}