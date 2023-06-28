package com.gas.app.entity.currency;

import java.util.Arrays;
import java.util.List;

public enum StandardCurrencyEnum {
    USD,
    EUR,
    UAH;
    static public List<String> toList(){
        return Arrays.stream(values()).map(Enum::name).toList();
    }
}
