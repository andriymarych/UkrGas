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
@Service("PERSONAL_GAS_ACCOUNT_CHANGE_MENU_SELECT")
@RequiredArgsConstructor
public class PersonalAccountChangeMenuSelectCommand implements Command {
    private final TelegramUserService telegramUserService;
    private  boolean isPersonAccountChanged;
    @Override
    public SendMessage execute(Update update) {
        changePersonalAccount(update);
        return buildSendMessageWithKeyboardMarkup(update,
                changePersonalAccount(update));
    }

    public String changePersonalAccount(Update update){
        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        String selectedPersonalAccount = update.getMessage().getText();
        List<PersonalGasAccount> personalGasAccounts = telegramUserService.getPersonalAccountListByUsername(username);
        for(var personalAccount : personalGasAccounts){
            if(personalAccount.getAccountNumber().equals(selectedPersonalAccount)){
                user.setCurrentPersonalGasAccount(personalAccount);
                user.setBotState(BotState.MAIN_MENU_SELECT);
                isPersonAccountChanged = true;
                return "Поточний особовий рахунок №" + personalAccount.getAccountNumber() + " успішно змінено";
            }
        }
        return "Введіть коректний номер особового рахунку";
    }
    public SendMessage buildSendMessageWithKeyboardMarkup(Update update, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        if(isPersonAccountChanged) {
            sendMessage.setReplyMarkup(initializeReplyKeyboardMarkup(update));
        }
        return sendMessage;
    }
    public ReplyKeyboardMarkup initializeReplyKeyboardMarkup(Update update){
        List<String> menuItems = getMenuItems();
        return ReplyKeyboardMarkupBuilder.build(menuItems);
    }
    public List<String> getMenuItems(){
        return  List.of(
                "ПОКАЗНИКИ",
                "ПЛАТЕЖІ",
                "РОЗРАХУНКИ",
                "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО",
                "ОСОБОВІ РАХУНКИ");
    }
}
