package com.gas.app.dto.personalAccount.payment;

import java.sql.Timestamp;

public record PaymentDto(long id, double amountPaid, Timestamp timestamp) {
}
