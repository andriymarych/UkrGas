package com.gas.app.dto;

import com.gas.app.entity.Meterage;
import com.gas.app.entity.PersonalGasAccount;

import java.util.List;

public record MeterageResponseDto(PersonalGasAccount personalGasAccount, List<Meterage> meterages) {
}
