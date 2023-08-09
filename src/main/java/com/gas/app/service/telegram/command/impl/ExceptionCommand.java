package com.gas.app.service.telegram.command.impl;

import com.gas.app.service.telegram.command.Command;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("EXCEPTION")
public class ExceptionCommand implements Command {
    @Override
    public SendMessage execute(Update update) {

        return buildSendMessage(update,
                "Ми не можемо обробити ваш запит, введіть коректну команду");
    }

    public SendMessage buildSendMessage(Update update, String message) {

        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
    }
}
