package com.gas.app.service.telegram.command.impl.personalAccount.payments;

import com.gas.app.entity.personalAccount.Address;
import com.gas.app.entity.personalAccount.Payment;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.service.personalAccount.PaymentService;
import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service("PAYMENTS")
@RequiredArgsConstructor
public class PaymentsCommand implements Command {


    private final TelegramUserService telegramUserService;
    private final PaymentService paymentService;

    @Override
    public SendMessage execute(Update update) {

        String username = update.getMessage().getFrom().getUserName();
        TelegramUser user = telegramUserService.getTelegramUserByUsername(username);
        PersonalGasAccount currentPersonalGasAccount = user.getCurrentPersonalGasAccount();

        return buildSendMessageWithKeyboardMarkup(update,
                getPayments(currentPersonalGasAccount));
    }

    private String getPayments(PersonalGasAccount personalGasAccount) {

        List<Payment> payments = paymentService
                .getPaymentsByPersonalAccountId(personalGasAccount.getId());


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Історія платежів за особовим рахунком ")
                .append(personalGasAccount.getAccountNumber())
                .append(getAddress(personalGasAccount.getAddress()));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        payments
                .stream()
                .limit(10)
                .sorted(Comparator.comparing(Payment::getId).reversed())
                .forEach(payment ->
                        stringBuilder.append(getFormattedDate(payment.getTimestamp()))
                                .append(" - ")
                                .append(decimalFormat.format(payment.getAmountPaid()))
                                .append("грн\n"));

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

        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
    }


}
