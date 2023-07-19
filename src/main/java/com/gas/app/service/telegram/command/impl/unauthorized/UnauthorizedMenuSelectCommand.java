package com.gas.app.service.telegram.command.impl.unauthorized;

import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.menu.MenuItemResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service("UNAUTHORIZED_MENU_SELECT")
@RequiredArgsConstructor
public class UnauthorizedMenuSelectCommand implements Command {

    private final MenuItemResolver menuItemResolver;

    @Override
    public SendMessage execute(Update update) {
        List<String> menuItems = List.of("ДОБАВИТИ ОСОБОВИЙ РАХУНОК", "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО");
        return menuItemResolver.execute(update, menuItems);
    }

}
