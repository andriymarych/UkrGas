package com.gas.app.dto;

import com.gas.app.entity.Payment;
import com.gas.app.entity.PersonalGasAccount;

import java.util.List;

public record PaymentResponseDto(PersonalGasAccount personalGasAccount, List<Payment> meterages) {
}
