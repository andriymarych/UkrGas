package com.gas.app.service.telegram.command.impl.personalAccount.meterReading;

import com.gas.app.dto.personalAccount.meterReading.MeterReadingRequestDto;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.exception.ServiceException;
import com.gas.app.service.personalAccount.MeterReadingService;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.command.MenuItemResolver;
import com.gas.app.service.telegram.service.TelegramUserService;
import com.gas.app.service.telegram.tool.ReplyKeyboardMarkupBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service("METER_READING_SAVE_MENU_INPUT")
@RequiredArgsConstructor
public class MeterReadingSaveMenuSelectCommand implements Command {

    private final TelegramUserService telegramUserService;
    private final PersonalGasAccountService personalGasAccountService;
    private final MeterReadingService meterReadingService;
    private final MenuItemResolver menuItemResolver;
    private boolean mainMenuFlag;

    @Override
    public SendMessage execute(Update update) {

        String username = update.getMessage().getFrom().getUserName();
        TelegramUser telegramUser = telegramUserService.getTelegramUserByUsername(username);
        PersonalGasAccount personalGasAccount = personalGasAccountService
                .getAccountByAccountId(
                        telegramUser.getCurrentPersonalGasAccount().getId()
                );
        String meterReadingValueStr = update.getMessage().getText();

        if (!meterReadingValueStr.matches("\\d+")) {
            if(meterReadingValueStr.equals("ГОЛОВНЕ МЕНЮ")){
                mainMenuFlag = true;
                telegramUser.setBotState(BotState.MAIN_MENU_SELECT);
                return buildSendMessageWithKeyboardMarkup(update,
                        "Для взаємодії скористайтеся контекстним меню");
            }
            return buildSendMessageWithKeyboardMarkup(update,
                    "Введіть значення показання лічильника, який складається лише із цифр");
        }

        Double meterReadingValue = Double.valueOf(meterReadingValueStr);
        try{
            meterReadingService.saveMeterReading(personalGasAccount.getId(), new MeterReadingRequestDto(meterReadingValue));
            mainMenuFlag = true;
            telegramUser.setBotState(BotState.MAIN_MENU_SELECT);
            return buildSendMessageWithKeyboardMarkup(update,
                    "Показники лічильника №" + personalGasAccount.getGasMeterNumber() + " успішно передано");
        }catch(ServiceException e){

            return buildSendMessageWithKeyboardMarkup(update,
                    "Введіть коректне значення лічильника");
        }
    }

    public SendMessage buildSendMessageWithKeyboardMarkup(Update update, String message) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        List<String> menuItems ;
        if(mainMenuFlag) {
            menuItems = getMainMenuItems();
            sendMessage.setReplyMarkup(ReplyKeyboardMarkupBuilder.build(menuItems));
        }

        return sendMessage;
    }

    public List<String> getMainMenuItems(){

        return  List.of(
                "ПОКАЗНИКИ",
                "ПЛАТЕЖІ",
                "РОЗРАХУНКИ",
                "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО",
                "ОСОБОВІ РАХУНКИ");
    }
}
