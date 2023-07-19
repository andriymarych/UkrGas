package com.gas.app.service.telegram.command;

import com.gas.app.entity.telegram.BotState;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class MenuItemResolver {
    private final CommandContainer commandContainer;

    public MenuItemResolver(@Lazy CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }
    public SendMessage execute(Update update, List<String> menuItems){
        String selectedMenuItem = update.getMessage().getText();
        if (menuItems.contains(selectedMenuItem)) {
            return commandContainer.get(BotState.getStateEnumStr(selectedMenuItem)).execute(update);
        } else {
            return commandContainer.get("EXCEPTION").execute(update);
        }
    }


}
