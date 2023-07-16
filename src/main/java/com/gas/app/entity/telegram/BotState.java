package com.gas.app.entity.telegram;

import java.util.Arrays;
import java.util.List;

public enum BotState {
    REGISTRATION("РЕЄCТРАЦІЯ"),
    CONFIRMATION_OF_REGISTRATION("ПІДТВЕРДЖЕННЯ РЕЄСТРАЦІЇ"),
    METER_READING("ПОКАЗНИКИ"),
    PAYMENTS("ПЛАТЕЖІ"),
    CALCULATIONS("РОЗРАХУНКИ");
    private final String state;

    BotState(String state) {
        this.state = state;
    }

    static public List<String> toList() {
        return Arrays.stream(values()).map(Enum::name).toList();
    }

    public String getState() {
        return state;
    }
}
