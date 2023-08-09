package com.gas.app.service.telegram.command;

import com.gas.app.service.telegram.command.impl.ExceptionCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandContainer {

    private final Map<String, Command> commandMap;

    @Autowired
    public CommandContainer(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public Command get(String command) {
        return commandMap.getOrDefault(command, new ExceptionCommand());
    }

}
