package com.gas.app.service.telegram.command.impl.registration;

import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.telegram.TelegramUserService;
import com.gas.app.service.telegram.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Service("PERSONAL_GAS_ACCOUNT_REGISTRATION")
@RequiredArgsConstructor
public class PersonalGasAccountRegistrationCommand implements Command {

    private final TelegramUserService telegramUserService;

    @Override
    @Transactional
    public SendMessage execute(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.findTelegramUserByUsername(username);
        user.setBotState(BotState.PERSONAL_GAS_ACCOUNT_ADDING);
        return buildSendMessage(update,
                        "Щоб добавити особовий рахунок, введіть його номер\n" +
                        "Наприклад: XXXXXXXXXX (тільки цифри)");
    }


    public SendMessage buildSendMessage(Update update, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;
    }
}
