package com.gas.app.service.telegram.command.impl.personalAccount.calculations;

import com.gas.app.entity.personalAccount.Address;
import com.gas.app.entity.personalAccount.Calculation;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.personalAccount.CalculationService;
import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service("CALCULATIONS")
@RequiredArgsConstructor
public class CalculationsCommand implements Command {


    private final TelegramUserService telegramUserService;
    private final CalculationService calculationService;

    @Override
    public SendMessage execute(Update update) {

        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        PersonalGasAccount currentPersonalGasAccount = user.getCurrentPersonalGasAccount();

        return buildSendMessageWithKeyboardMarkup(update,
                getCalculations(currentPersonalGasAccount));
    }

    private String getCalculations(PersonalGasAccount personalGasAccount) {

        List<Calculation> calculations = calculationService
                .getCalculationsByPersonalAccountId(personalGasAccount.getId());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Історія розрахунків за особовим рахунком ")
                .append(personalGasAccount.getAccountNumber())
                .append(getAddress(personalGasAccount.getAddress()));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        calculations
                .stream()
                .limit(10)
                .sorted(Comparator.comparing(Calculation::getId))
                .forEach(payment ->
                        stringBuilder.append(getFormattedDate(payment.getDate().toLocalDate()))
                                .append(" - ")
                                .append(decimalFormat.format(payment.getAmountConsumed()))
                                .append("м³ - ")
                                .append(decimalFormat.format(payment.getAccruedPayment()))
                                .append("грн\n"));

        return stringBuilder.toString();
    }

    public String getFormattedDate(LocalDate date) {

        List<String> month = List.of(
                "Січень",
                "Лютий",
                "Березень",
                "Квітень",
                "Травень",
                "Червень",
                "Липень",
                "Серпень",
                "Вересень",
                "Жовтень",
                "Листопад",
                "Грудень");


        return month.get(date.getMonthValue()) + " " + date.getYear();
    }

    public String getAddress(Address address) {
        return "\n(вул. " + address.getStreet() + ", буд. " + address.getHouseNumber() + "):\n\n";
    }

    public SendMessage buildSendMessageWithKeyboardMarkup(Update update, String message) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
    }
}
