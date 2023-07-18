package com.gas.app.service.telegram.command.impl.registration;

import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.repository.telegram.TelegramUserRepository;
import com.gas.app.service.telegram.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Service("TELEGRAM_USER_REGISTRATION")
@RequiredArgsConstructor
public class TelegramUserRegistrationCommand implements Command {

    private final TelegramUserRepository telegramUserRepository;

    @Override
    @Transactional
    public SendMessage execute(Update update) {
        registerTelegramUser(update);
        return buildSendMessage(update,
                "Вітаємо\n" +
                        "На жаль, ми не змогли виявити вашого особового рахунку\n" +
                        "Щоб добавити його, введіть номер особового рахунку \n" +
                        "Наприклад: XXXXXXXXXX (тільки цифри)");
    }

    public void registerTelegramUser(Update update) {
        TelegramUser user = new TelegramUser();
        user.setUsername(update.getMessage().getFrom().getUserName());
        user.setChatId(update.getMessage().getChatId());
        user.setBotState(BotState.UNAUTHORIZED_MENU);
        telegramUserRepository.save(user);
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
