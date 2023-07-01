package com.gas.app.service.personalAccount;


import com.gas.app.dto.personalAccount.payment.PaymentRequestDto;
import com.gas.app.dto.personalAccount.payment.PaymentResponseDto;
import com.gas.app.dto.user.UserSessionDto;
import com.gas.app.entity.personalAccount.Payment;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.personalAccount.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PersonalGasAccountService accountService;
    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentsByPersonalAccountId(UserSessionDto userSessionDto,
                                                             Long personalAccountId) {

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(userSessionDto, personalAccountId);

        List<Payment> payments = paymentRepository.
                findPaymentsByPersonalAccountId(personalGasAccount.getId())
                .orElseThrow(
                        () -> new ServiceException("Could not find payments with personal gas account["
                                + personalAccountId + "]", HttpStatus.NOT_FOUND));

        return new PaymentResponseDto(personalGasAccount, payments);
    }

    @Transactional
    public Payment savePayment(PaymentRequestDto requestDto) {

        validateAmountPaid(requestDto.amountPaid());

        PersonalGasAccount personalGasAccount = accountService.
                getAccountByAccountId(requestDto.userSession(), requestDto.gasAccountId());

        Payment payment = new Payment(requestDto.amountPaid());
        payment.setPersonalGasAccount(personalGasAccount);
        personalGasAccount.setBalance(personalGasAccount.getBalance() + payment.getAmountPaid());

        return paymentRepository.save(payment);
    }

    public void validateAmountPaid(Double amountPaid) {

        if (amountPaid <= 0) {
            throw new ServiceException("The amount paid must be greater than 0",
                    HttpStatus.CONFLICT);
        }

    }
}
