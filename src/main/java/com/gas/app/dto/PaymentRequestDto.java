package com.gas.app.dto;

public record PaymentRequestDto(UserSessionDto userSession,
                                Long gasAccountId, Double amountPaid) {
}

