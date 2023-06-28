package com.gas.app.dto.personalAccount.payment;

import com.gas.app.dto.user.UserSessionDto;

public record PaymentRequestDto(UserSessionDto userSession,
                                Long gasAccountId, Double amountPaid) {
}

