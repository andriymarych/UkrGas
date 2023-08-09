package com.gas.app.service.telegram.command.impl.fuelPrice;

import com.gas.app.dto.fuel.FuelPriceChangeDto;
import com.gas.app.entity.currency.StandardCurrencyEnum;
import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.fuel.FuelPriceService;
import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.service.TelegramUserService;
import com.gas.app.service.telegram.tool.ReplyKeyboardMarkupBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service("FUEL_PRICE")
@RequiredArgsConstructor
public class FuelPriceCommand implements Command {

    private final FuelPriceService fuelPriceService;
    private final TelegramUserService telegramUserService;

    @Override
    public SendMessage execute(Update update) {

        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        String currencyStr = update.getMessage().getText();
        StandardCurrencyEnum currency = StandardCurrencyEnum.valueOf(currencyStr);
        if(telegramUserService.isUserAuthorized(username)){
            user.setBotState(BotState.MAIN_MENU_SELECT);
        }else{
            user.setBotState(BotState.UNAUTHORIZED_MENU);
        }
        return buildSendMessageWithKeyboardMarkup(update,
                getFuelPrices(currency));
    }

    private String getFuelPrices(StandardCurrencyEnum currency) {

        List<FuelPriceChangeDto> fuelPrices = fuelPriceService.getFuelPrices(currency);
        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US);
        stringBuilder.append("Актуальні ціни на паливо \nСтаном на ").append(LocalDate.now().format(dateFormatter)).append(":\n");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        fuelPrices.forEach(fuelPrice ->
                stringBuilder.append(fuelPrice.fuelType())
                        .append("  ")
                        .append(decimalFormat.format(fuelPrice.price()))
                        .append("(")
                        .append(getSign(fuelPrice.priceChangePct()))
                        .append(decimalFormat.format(fuelPrice.priceChangePct()))
                        .append("%)\n"));

        return stringBuilder.toString();
    }
    public String getSign(Double number){
        return number >=0 ? "+" : "";
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
        boolean isUserAuthorized = telegramUserService.isUserAuthorized(username);
        List<String> menuItems;
        if(isUserAuthorized){
            menuItems = getAuthorizedMenuItems();
        }else{
            menuItems = getUnauthorizedMenuItems();
        }
        return ReplyKeyboardMarkupBuilder.build(menuItems);
    }
    public List<String> getUnauthorizedMenuItems(){

        return List.of("ДОБАВИТИ ОСОБОВИЙ РАХУНОК",
                "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО");
    }
    public List<String> getAuthorizedMenuItems(){

        return  List.of(
                "ПОКАЗНИКИ",
                "ПЛАТЕЖІ",
                "РОЗРАХУНКИ",
                "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО",
                "ОСОБОВІ РАХУНКИ");
    }

}
