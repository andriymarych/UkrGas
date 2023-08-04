package com.gas.app.service.telegram.command.impl.personalAccount.meterReading;

import com.gas.app.entity.personalAccount.Address;
import com.gas.app.entity.personalAccount.MeterReading;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service("METER_READING_MENU")
@RequiredArgsConstructor
public class MeterReadingMenuCommand implements Command {

    private final MeterReadingService meterReadingService;
    private final TelegramUserService telegramUserService;

    @Override
    public SendMessage execute(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        user.setBotState(BotState.METER_READING_MENU_SELECT);
        PersonalGasAccount currentPersonalGasAccount = user.getCurrentPersonalGasAccount();

        return buildSendMessageWithKeyboardMarkup(update,
                getMeterReadings(currentPersonalGasAccount));
    }

    private String getMeterReadings(PersonalGasAccount personalGasAccount) {
        List<MeterReading> meterReadings = meterReadingService
                .getMeterReadingsByPersonalAccountId(personalGasAccount.getId());


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Показання лічильника за особовим рахунком ")
                .append(personalGasAccount.getAccountNumber())
                .append(getAddress(personalGasAccount.getAddress()));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        meterReadings
                .stream()
                .limit(5)
                .sorted(Comparator.comparing(MeterReading::getId).reversed())
                .forEach(meterReading ->
                stringBuilder.append(getFormattedDate(meterReading.getDate()))
                        .append(" - ")
                        .append(decimalFormat.format(meterReading.getMeterReading()))
                        .append("м³\n"));
        return stringBuilder.toString();
    }
    public String getFormattedDate(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormatter.format(date);
    }
    public String getAddress(Address address){
        return "\n(вул. " + address.getStreet() + ", буд. " + address.getHouseNumber() + "):\n\n";
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

        List<String> menuItems  = getMenuItems();
        return ReplyKeyboardMarkupBuilder.build(menuItems);
    }
    public List<String> getMenuItems(){
        return List.of("ПЕРЕДАТИ ПОКАЗАННЯ",
                "ГОЛОВНЕ МЕНЮ");
    }

}
