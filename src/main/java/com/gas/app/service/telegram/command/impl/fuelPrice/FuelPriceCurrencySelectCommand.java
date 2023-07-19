package com.gas.app.service.telegram.command.impl.fuelPrice;

import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.service.TelegramUserService;
import com.gas.app.service.telegram.tool.ReplyKeyboardMarkupBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service("FUEL_PRICE_CURRENCY_MENU_SELECT")
@RequiredArgsConstructor
public class FuelPriceCurrencySelectCommand implements Command {
    private final TelegramUserService telegramUserService;
    @Override
    public SendMessage execute(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        user.setBotState(BotState.FUEL_PRICE);

        return buildSendMessageWithKeyboardMarkup(update,
                        "Оберіть валюту для відображення цін на паливо");

    }
    public SendMessage buildSendMessageWithKeyboardMarkup(Update update, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        List<String> menuItems = getMenuItems();
        sendMessage.setReplyMarkup(ReplyKeyboardMarkupBuilder.build(menuItems));
        return sendMessage;
    }
    public List<String> getMenuItems(){
        return List.of(
                "UAH",
                "USD",
                "EUR"
        );
    }
}
