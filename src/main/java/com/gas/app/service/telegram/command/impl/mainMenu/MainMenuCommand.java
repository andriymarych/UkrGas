package com.gas.app.service.telegram.command.impl.mainMenu;

import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.service.TelegramUserService;
import com.gas.app.service.telegram.tool.ReplyKeyboardMarkupBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Service("MAIN_MENU")
@RequiredArgsConstructor
public class MainMenuCommand implements Command {
    private final TelegramUserService telegramUserService;

    @Override
    public SendMessage execute(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        user.setBotState(BotState.MAIN_MENU_SELECT);
        return buildSendMessageWithKeyboardMarkup(update);

    }
    public SendMessage buildSendMessageWithKeyboardMarkup(Update update) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Для взаємодії скористаєтеся контекстним меню")
                .build();
        sendMessage.setReplyMarkup(initializeReplyKeyboardMarkup(update));

        return sendMessage;
    }
    public ReplyKeyboardMarkup initializeReplyKeyboardMarkup(Update update){

        List<String> menuItems  = getMenuItems();
        return ReplyKeyboardMarkupBuilder.build(menuItems);
    }
    public List<String> getMenuItems(){

        return List.of(
                "ПОКАЗНИКИ",
                "ПЛАТЕЖІ",
                "РОЗРАХУНКИ",
                "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО",
                "ОСОБОВІ РАХУНКИ");
    }
}
