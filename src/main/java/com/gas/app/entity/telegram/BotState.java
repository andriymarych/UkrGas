package com.gas.app.entity.telegram;

import java.util.Arrays;
import java.util.List;

public enum BotState {
    START("/start"),
    UNAUTHORIZED_MENU("НЕАВТОРИЗОВАНЕ МЕНЮ"),
    UNAUTHORIZED_MENU_SELECT("НЕАВТОРИЗОВАНИЙ ВИБІР ПОЗИЦІЇ МЕНЮ"),
    TELEGRAM_USER_REGISTRATION("РЕЄСТРАЦІЯ КОРИСТУВАЧА ТЕЛЕГРАМУ"),
    PERSONAL_GAS_ACCOUNT_REGISTRATION("ДОБАВИТИ ОСОБОВИЙ РАХУНОК"),
    FUEL_PRICE("ЦІНИ НА ПАЛИВО"),
    FUEL_PRICE_CURRENCY_MENU_SELECT("АКТУАЛЬНІ ЦІНИ НА ПАЛИВО"),
    PERSONAL_GAS_ACCOUNT_ADDING("ДОБАВЛЕННЯ ОСОБОВОГО РАХУНКУ"),
    PERSONAL_GAS_ACCOUNT_VERIFYING("ПІДТВЕРДЖЕННЯ ОСОБОВОГО РАХУНКУ"),
    PERSONAL_GAS_ACCOUNT_MENU("ОСОБОВІ РАХУНКИ"),
    PERSONAL_GAS_ACCOUNT_MENU_SELECT("ВИБІР ОПЦІЇ МЕНЮ ОСОБОВИХ РАХУНКІВ"),
    PERSONAL_GAS_ACCOUNT_CHANGE_MENU("ЗМІНИТИ ПОТОЧНИЙ ОСОБОВИЙ РАХУНОК"),
    PERSONAL_GAS_ACCOUNT_CHANGE_MENU_SELECT("ВИБІР ОСОБОВОГО РАХУНКУ"),
    MAIN_MENU("ГОЛОВНЕ МЕНЮ"),
    MAIN_MENU_SELECT("ВИБІР ОПЦІЇ ГОЛОВНОГО МЕНЮ"),
    METER_READING_MENU("ПОКАЗНИКИ"),
    METER_READING_MENU_SELECT("ВИБІР ОПЦІЇ МЕНЮ РОЗДІЛУ ПОКАЗНИКІВ"),
    METER_READING_SAVE_MENU("ПЕРЕДАТИ ПОКАЗАННЯ"),
    METER_READING_SAVE_MENU_INPUT("ПЕРЕДАЧА ПОКАЗНИКА ЛІЧИЛЬНИКА"),
    PAYMENTS("ПЛАТЕЖІ"),
    CALCULATIONS("РОЗРАХУНКИ");
    private final String state;

    BotState(String state) {
        this.state = state;
    }

    public static String getStateEnumStr(String stateStr) {
        for(BotState botState : values()){
            if(botState.state.equals(stateStr)){
                return botState.name();
            }
        }
        return null;
    }
}
