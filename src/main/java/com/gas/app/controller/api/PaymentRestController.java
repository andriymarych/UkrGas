package com.gas.app.controller.api;


import com.gas.app.dto.PaymentRequestDto;
import com.gas.app.dto.PaymentResponseDto;
import com.gas.app.dto.UserSessionDto;
import com.gas.app.entity.Payment;
import com.gas.app.service.PaymentService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/personal-account/")
public class PaymentRestController {

    private final PaymentService paymentService;
    @GetMapping("/{personalAccountId}/payments/")
    @Transactional
    public ResponseEntity<Object> getPayments(@PathVariable Long personalAccountId,
                                               @RequestParam Long userId,
                                               @RequestParam Long authId) {

        UserSessionDto userSessionDto = new UserSessionDto(userId, authId);
        PaymentResponseDto paymentResponseDto = paymentService
                .getPaymentsByPersonalAccountId(userSessionDto, personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, paymentResponseDto);
    }
    @PostMapping("/payment/")
    @Transactional
    public ResponseEntity<Object> savePayment(@RequestBody PaymentRequestDto requestDto) {

        Payment payment = paymentService
                .savePayment(requestDto);

        return ResponseHandler
                .generateResponse("Payment ["
                                + payment.getId() +
                                "] in the amount "
                                + payment.getAmountPaid() + " UAH has been successfully saved",
                        HttpStatus.OK, payment);
    }
}
