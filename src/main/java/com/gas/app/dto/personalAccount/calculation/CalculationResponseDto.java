package com.gas.app.dto.personalAccount.calculation;

import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.PersonalGasAccount;

import java.util.List;

public record CalculationResponseDto (PersonalGasAccount personalGasAccount, List<Calculation> calculations){
}
