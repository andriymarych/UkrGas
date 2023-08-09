package com.gas.app.service.telegram.command.impl.personalAccount.change;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
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
@Service("PERSONAL_GAS_ACCOUNT_CHANGE_MENU")
@RequiredArgsConstructor
public class PersonalAccountChangeMenuCommand implements Command {

    private final TelegramUserService telegramUserService;
    @Override
    public SendMessage execute(Update update) {

        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        user.setBotState(BotState.PERSONAL_GAS_ACCOUNT_CHANGE_MENU_SELECT);

        return buildSendMessageWithKeyboardMarkup(update,
                "Оберіть поточний особовий рахунок");
    }
    public SendMessage buildSendMessageWithKeyboardMarkup(Update update, String message) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        sendMessage.setReplyMarkup(initializeReplyKeyboardMarkup(update));

        return sendMessage;
    }
    public ReplyKeyboardMarkup initializeReplyKeyboardMarkup(Update update){

        String username = update.getMessage().getFrom().getUserName();
        List<String> menuItems  = getPersonalGasAccountsStr(username);

        return ReplyKeyboardMarkupBuilder.build(menuItems);
    }

    private List<String> getPersonalGasAccountsStr(String username) {

        List<PersonalGasAccount> personalAccounts = telegramUserService
                .getPersonalAccountListByUsername(username);

        return personalAccounts.stream().map(PersonalGasAccount::getAccountNumber).toList();
    }

}
