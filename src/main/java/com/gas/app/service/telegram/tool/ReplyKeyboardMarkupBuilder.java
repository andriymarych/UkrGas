package com.gas.app.service.telegram.tool;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkupBuilder {

    public static ReplyKeyboardMarkup build(List<String> menuItems){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        menuItems.forEach(menuItem -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(menuItem);
            keyboardRows.add(keyboardRow);
        });

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }
}
