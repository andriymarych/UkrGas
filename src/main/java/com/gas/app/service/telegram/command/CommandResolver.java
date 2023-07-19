package com.gas.app.service.telegram.command;

import com.gas.app.entity.telegram.BotState;
import com.gas.app.entity.telegram.TelegramUser;
import com.gas.app.repository.telegram.TelegramUserRepository;
import com.gas.app.service.telegram.CommandContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandResolver {

    private final TelegramUserRepository telegramUserRepository;
    private final CommandContainer commandContainer;

    @Transactional
    public SendMessage getMessageResponse(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        Optional<TelegramUser> user = telegramUserRepository.findTelegramUserByUsername(username);
        if (user.isEmpty()) {
            return commandContainer.get("START").execute(update);
        }
        BotState state = user.get().getBotState();
        return commandContainer
                .get(state.name())
                .execute(update);
    }
}
