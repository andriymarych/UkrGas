package com.gas.app.service.telegram.command.impl.personalAccount.meterReading;

import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.personalAccount.MeterReadingService;
import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.service.TelegramUserService;
import com.gas.app.service.telegram.tool.ReplyKeyboardMarkupBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service("METER_READING_SAVE_MENU")
@RequiredArgsConstructor
public class MeterReadingSaveMenuCommand implements Command {

    private final MeterReadingService meterReadingService;
    private final TelegramUserService telegramUserService;
    private boolean isBillingPeriodClosed;

    @Override
    public SendMessage execute(Update update) {

        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        PersonalGasAccount personalGasAccount = user.getCurrentPersonalGasAccount();
        isBillingPeriodClosed = isBillingPeriodClosed(personalGasAccount.getId());

        if (isBillingPeriodClosed) {
            user.setBotState(BotState.MAIN_MENU_SELECT);
            return buildSendMessageWithKeyboardMarkup(update,
                    "Розрахунковий період закрито\n" +
                            "Показання за поточний місяць уже передано");
        }

        user.setBotState(BotState.METER_READING_SAVE_MENU_INPUT);
        Long gasMeterNumber = personalGasAccount.getGasMeterNumber();

        return buildSendMessageWithKeyboardMarkup(update,
                "Введіть показання лічильника №" + gasMeterNumber + " станом на " + getFormattedDate(LocalDate.now()) + "\n");
    }

    public boolean isBillingPeriodClosed(Long personalAccountId) {

        return meterReadingService.isBillingPeriodClosed(personalAccountId);
    }
    public String getFormattedDate(LocalDate date) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US);

        return dateFormatter.format(date);
    }
    public SendMessage buildSendMessageWithKeyboardMarkup(Update update, String message) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        List<String> menuItems ;
        if(isBillingPeriodClosed){
            menuItems = getMainMenuItems();
        }else{
            menuItems = getMeterReadingSaveMenuItems();
        }
        sendMessage.setReplyMarkup(ReplyKeyboardMarkupBuilder.build(menuItems));

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
    public List<String> getMeterReadingSaveMenuItems(){

        return  List.of(
                "ГОЛОВНЕ МЕНЮ");
    }
}
