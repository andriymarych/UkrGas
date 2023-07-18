package com.gas.app.service.telegram.command.impl.unauthorized;

import com.gas.app.entity.telegram.BotState;
import com.gas.app.service.telegram.CommandContainer;
import com.gas.app.service.telegram.command.Command;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service("UNAUTHORIZED_SELECT_MENU_ITEM")
public class UnauthorizedSelectMenuItemCommand implements Command {
    private final CommandContainer commandContainer;

    public UnauthorizedSelectMenuItemCommand(@Lazy CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }

    @Override
    @Transactional
    public SendMessage execute(Update update) {
        List<String> menuItems = List.of("ДОБАВИТИ ОСОБОВИЙ РАХУНОК", "АКТУАЛЬНІ ЦІНИ НА ПАЛЬНЕ");
        String selectedMenuItem = update.getMessage().getText();
        if (menuItems.contains(selectedMenuItem)) {
            return commandContainer.get(BotState.getStateEnumStr(selectedMenuItem)).execute(update);
        } else {
            return commandContainer.get("EXCEPTION").execute(update);
        }
    }


}
