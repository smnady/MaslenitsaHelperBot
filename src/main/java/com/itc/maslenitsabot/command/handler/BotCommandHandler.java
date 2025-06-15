package com.itc.maslenitsabot.command.handler;

import com.itc.maslenitsabot.command.BotCommand;
import com.itc.maslenitsabot.command.BotCommandEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Обработчик команд бота.
 * <p>
 * Используется для обработки и выполнения пользовательских команд с помощью зарегистрированных
 * объектов {@link BotCommand}.
 * </p>
 *
 * @author smnadya
 * @since 2025.06.15
 */
@Component
public class BotCommandHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(BotCommandHandler.class);

    private final List<BotCommand> botCommands;

    public BotCommandHandler(List<BotCommand> botCommands) {
        this.botCommands = botCommands;
    }

    public void onCommandPrepare(BotCommandEvent event) {
        final String prompt = event.prompt();
        LOGGER.debug("Processing the command: {}, from User: userName={}, firstName={}, lastname={}",
                prompt,
                event.message().getFrom().getUserName(),
                event.message().getFrom().getFirstName(),
                event.message().getFrom().getLastName()
        );
        botCommands.stream()
                .filter(command -> command.canExecute(prompt))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot resolve command " + prompt))
                .execute(prompt, event.sendMessage(), event.message());
    }

}
