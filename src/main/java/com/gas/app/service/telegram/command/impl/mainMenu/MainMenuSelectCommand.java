package com.gas.app.service.telegram.command.impl.mainMenu;

import com.gas.app.service.telegram.command.Command;
import com.gas.app.service.telegram.command.MenuItemResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service("MAIN_MENU_SELECT")
@RequiredArgsConstructor
public class MainMenuSelectCommand implements Command {

    private final MenuItemResolver menuItemResolver;

    @Override
    public SendMessage execute(Update update) {
        List<String> menuItems =List.of(
                "ПОКАЗНИКИ",
                "ПЛАТЕЖІ",
                "РОЗРАХУНКИ",
                "АКТУАЛЬНІ ЦІНИ НА ПАЛИВО",
                "ОСОБОВІ РАХУНКИ");
        menuItemResolver.execute(update,menuItems);
        return menuItemResolver.execute(update, menuItems);
    }
}
