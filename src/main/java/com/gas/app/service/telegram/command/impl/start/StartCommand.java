package com.gas.app.service.telegram.command.impl.start;

import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.repository.telegram.TelegramUserRepository;
import com.gas.app.service.telegram.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
@Service("START")
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final TelegramUserRepository telegramUserRepository;
    @Override
    public SendMessage execute(Update update) {
        registerTelegramUser(update);
        return buildSendMessage(update,
                "Вітаємо, " + update.getMessage().getFrom().getFirstName() + "\n" +
                        "Для початку роботи із нашим ботом скористайтеся контекстним меню");
    }

    public void registerTelegramUser(Update update) {
        TelegramUser user = new TelegramUser();
        user.setUsername(update.getMessage().getFrom().getUserName());
        user.setChatId(update.getMessage().getChatId());
        user.setBotState(BotState.UNAUTHORIZED_MENU_SELECT);
        telegramUserRepository.save(user);
    }


    public SendMessage buildSendMessage(Update update, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<String> menuItems = List.of("ДОБАВИТИ ОСОБОВИЙ РАХУНОК", "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО");
        menuItems.forEach(menuItem -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(menuItem);
            keyboardRows.add(keyboardRow);
        });
        keyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(keyboardMarkup);

        return sendMessage;
    }
}
