package com.gas.app.service.telegram.bot;

import com.gas.app.service.telegram.command.CommandResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String botUsername;
    private CommandResolver commandResolver;

    public TelegramBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = commandResolver.getMessageResponse(update);
            try {
                this.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Autowired
    public void setCommandService(CommandResolver commandResolver) {
        this.commandResolver = commandResolver;
    }


    public String getBotUsername() {
        return botUsername;
    }

}
