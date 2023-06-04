package com.gas.app.dto;

import com.gas.app.entity.Calculation;
import com.gas.app.entity.PersonalGasAccount;

import java.util.List;

public record CalculationResponseDto(PersonalGasAccount personalGasAccount,
                                     List<Calculation> calculations) {
}
