package com.gas.app.service.telegram.command.impl.personalAccount.meterReading;

import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.command.MenuItemResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@Service("METER_READING_MENU_SELECT")
@RequiredArgsConstructor
public class MeterReadingMenuSelectCommand implements Command {
    private final MenuItemResolver menuItemResolver;

    @Override
    public SendMessage execute(Update update) {
        List<String> menuItems = List.of("ПЕРЕДАТИ ПОКАЗАННЯ", "ГОЛОВНЕ МЕНЮ");
        return menuItemResolver.execute(update, menuItems);
    }
}
