package com.gas.app.service.telegram.command.impl.unauthorizedMenu;

import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.command.MenuItemResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service("UNAUTHORIZED_MENU")
@RequiredArgsConstructor
public class UnauthorizedMenuCommand implements Command {

    private final MenuItemResolver menuItemResolver;

    @Override
    public SendMessage execute(Update update) {
        List<String> menuItems = List.of("ДОБАВИТИ ОСОБОВИЙ РАХУНОК", "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО");
        return menuItemResolver.execute(update, menuItems);
    }

}
