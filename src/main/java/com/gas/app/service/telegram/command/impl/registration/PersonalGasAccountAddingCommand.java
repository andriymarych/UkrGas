package com.gas.app.service.telegram.command.impl.registration;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.telegram.TelegramUserRepository;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
import com.gas.app.service.telegram.authentication.TelegramAuthenticationService;
import com.gas.app.service.telegram.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("PERSONAL_GAS_ACCOUNT_ADDING")
@RequiredArgsConstructor
public class PersonalGasAccountAddingCommand implements Command {

    private final TelegramAuthenticationService authenticationService;
    private final PersonalGasAccountService personalGasAccountService;
    private final TelegramUserRepository telegramUserRepository;

    @Override
    @Transactional(noRollbackFor = ServiceException.class)
    public SendMessage execute(Update update) {

        String accountNumber = update.getMessage().getText();
        if (!accountNumber.matches("\\d+")) {

            return buildSendMessage(update,
                    "Введіть номер особового рахунку, який складається лише із цифр");
        }

        try {
            setPersonalGasAccount(update);
        } catch (ServiceException e) {
            return buildSendMessage(update,
                    "Особового рахунку №" + accountNumber + " не існує\n" +
                            "Повторіть спробу");
        }
        return buildSendMessage(update,
                "Для підтвердження власності особового рахунку " + accountNumber + " введіть номер лічильника:");
    }

    @Transactional
    public void setPersonalGasAccount(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = authenticationService.getUserByUsername(username);
        String accountNumber = update.getMessage().getText();
        PersonalGasAccount personalGasAccount = personalGasAccountService.getAccountByAccountNumber(accountNumber);
        authenticationService.addPersonalGasAccount(user, personalGasAccount);
        user.setBotState(BotState.PERSONAL_GAS_ACCOUNT_VERIFYING);
        telegramUserRepository.save(user);
    }


    public SendMessage buildSendMessage(Update update, String message) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
    }
}
