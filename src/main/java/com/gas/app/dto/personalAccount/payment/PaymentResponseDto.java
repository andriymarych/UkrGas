package com.gas.app.dto.personalAccount.payment;

import com.gas.app.entity.personalAccount.PersonalGasAccount;

import java.util.List;

public record PaymentResponseDto(PersonalGasAccount personalGasAccount, List<PaymentDto> payments) {
}
