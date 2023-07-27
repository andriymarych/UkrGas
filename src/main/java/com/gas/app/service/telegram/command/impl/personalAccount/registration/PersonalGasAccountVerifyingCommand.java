package com.gas.app.service.telegram.command.impl.personalAccount.registration;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.telegram.authentication.TelegramAuthenticationService;
import com.gas.app.service.telegram.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service("PERSONAL_GAS_ACCOUNT_VERIFYING")
@RequiredArgsConstructor
public class PersonalGasAccountVerifyingCommand implements Command {

    private final TelegramAuthenticationService authenticationService;

    @Override
    public SendMessage execute(Update update) {
        String accountNumber;
        String gasMeterNumber = update.getMessage().getText();

        if (!gasMeterNumber.matches("\\d+")) {
            return buildSendMessage(update,
                    "Введіть номер лічильника, який складається лише із цифр");
        }

        accountNumber = verifyGasMeterNumber(update);
        if (accountNumber == null) {
            return buildSendMessage(update,
                    "Ви ввели невірний номер лічильника\n" +
                            "Повторіть спробу");
        }
        return

                buildSendMessageWithKeyboardMarkup(update,
                        "Особовий рахунок №" + accountNumber + " успішно додано");

    }

    public String verifyGasMeterNumber(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = authenticationService.getUserByUsername(username);
        String gasMeterNumber = update.getMessage().getText();
        PersonalGasAccount personalGasAccount = authenticationService.authenticateGasAccount(user, Long.valueOf(gasMeterNumber));
        if (personalGasAccount != null) {
            user.setBotState(BotState.MAIN_MENU_SELECT);
            user.setCurrentPersonalGasAccount(personalGasAccount);
            return personalGasAccount.getAccountNumber();
        }
        return null;
    }

    public SendMessage buildSendMessage(Update update, String message) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
    }


    public SendMessage buildSendMessageWithKeyboardMarkup(Update update, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<String> menuItems = List.of(
                "ПОКАЗНИКИ",
                "ПЛАТЕЖІ",
                "РОЗРАХУНКИ",
                "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО",
                "ОСОБОВІ РАХУНКИ");
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
