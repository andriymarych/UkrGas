package com.gas.app.controller.api.personalAccount;


import com.gas.app.dto.personalAccount.payment.PaymentDto;
import com.gas.app.dto.personalAccount.payment.PaymentRequestDto;
import com.gas.app.dto.personalAccount.payment.PaymentResponseDto;
import com.gas.app.entity.personalAccount.Payment;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.service.personalAccount.PaymentService;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/personal-accounts/{personalAccountId}")
public class PaymentRestController {

    private final PersonalGasAccountService personalGasAccountService;
    private final PaymentService paymentService;
    @GetMapping("/payments")
    public ResponseEntity<Object> getPayments(@PathVariable Long personalAccountId) {

        PersonalGasAccount personalGasAccount = personalGasAccountService
                .getAccountByAccountId(personalAccountId);

        List<PaymentDto> paymentDtoList = paymentService
                .getPaymentsByPersonalAccountId(personalAccountId)
                .stream()
                .map(payment ->
                        new PaymentDto(
                                payment.getId(),
                                payment.getAmountPaid(),
                                payment.getTimestamp()))
                .toList();

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, new PaymentResponseDto(personalGasAccount,paymentDtoList));
    }
    @PostMapping("/payments")
    public ResponseEntity<Object> savePayment(@PathVariable Long personalAccountId,
                                              @RequestBody PaymentRequestDto requestDto) {

        Payment payment = paymentService
                .savePayment(personalAccountId, requestDto);

        return ResponseHandler
                .generateResponse("Payment ["
                                + payment.getId() +
                                "] in the amount "
                                + payment.getAmountPaid() + " UAH has been successfully saved",
                        HttpStatus.CREATED, payment);
    }
}
