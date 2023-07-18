package com.gas.app.entity.telegram;

import java.util.Arrays;
import java.util.List;

public enum BotState {
    UNAUTHORIZED_MENU("НЕАВТОРИЗОВАНЕ МЕНЮ"),
    UNAUTHORIZED_SELECT_MENU_ITEM("НЕАВТОРИЗОВАНИЙ ВИБІР ПОЗИЦІЇ МЕНЮ"),
    TELEGRAM_USER_REGISTRATION("РЕЄСТРАЦІЯ КОРИСТУВАЧА ТЕЛЕГРАМУ"),
    PERSONAL_GAS_ACCOUNT_REGISTRATION("ДОБАВИТИ ОСОБОВИЙ РАХУНОК"),
    SELECT_FUEL_PRICE_CURRENCY("АКТУАЛЬНІ ЦІНИ НА ПАЛИВО"),
    FUEL_PRICE(""),
    PERSONAL_GAS_ACCOUNT_ADDING(""),
    PERSONAL_GAS_ACCOUNT_VERIFYING("ПІДТВЕРДЖЕННЯ ПЕРСОНАЛЬНОГО АККАУНТУ"),
    MAIN_MENU("ГОЛОВНЕ МЕНЮ"),
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
    public static String getStateEnumStr(String stateStr) {
        for(BotState botState : values()){
            if(botState.state.equals(stateStr)){
                return botState.name();
            }
        }
        return null;
    }
    public String getState() {
        return state;
    }
}
