package com.gas.app.controller.api.personalAccount;


import com.gas.app.dto.personalAccount.payment.PaymentRequestDto;
import com.gas.app.dto.personalAccount.payment.PaymentResponseDto;
import com.gas.app.dto.user.UserSessionDto;
import com.gas.app.entity.personalAccount.Payment;
import com.gas.app.service.personalAccount.PaymentService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/personal-accounts/{personalAccountId}")
public class PaymentRestController {

    private final PaymentService paymentService;
    @GetMapping("/payments")
    public ResponseEntity<Object> getPayments(@PathVariable Long personalAccountId,
                                               @RequestParam Long userId,
                                               @RequestParam Long authId) {

        UserSessionDto userSessionDto = new UserSessionDto(userId, authId);
        PaymentResponseDto paymentResponseDto = paymentService
                .getPaymentsByPersonalAccountId(userSessionDto, personalAccountId);

        return ResponseHandler.generateResponse("OK",
                HttpStatus.OK, paymentResponseDto);
    }
    @PostMapping("/payments")
    public ResponseEntity<Object> savePayment(@PathVariable Long personalAccountId,
                                              @RequestBody PaymentRequestDto requestDto) {

        Payment payment = paymentService
                .savePayment(requestDto);

        return ResponseHandler
                .generateResponse("Payment ["
                                + payment.getId() +
                                "] in the amount "
                                + payment.getAmountPaid() + " UAH has been successfully saved",
                        HttpStatus.CREATED, payment);
    }
}
