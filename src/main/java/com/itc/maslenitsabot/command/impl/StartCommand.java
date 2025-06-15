package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.command.BotCommand;
import com.itc.maslenitsabot.common.Command;
import com.itc.maslenitsabot.keyboard.KeyboardFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static com.itc.maslenitsabot.command.CommandOrder.CO_CONCRETE;

/**
 * Команда для стартового взаимодействия с пользователем.
 * <p>
 * Отображает главное меню с доступными командами, используя клавиатуру,
 * созданную на основе перечисления {@link Command}.
 * </p>
 *
 * @author smnadya
 * @since 2025.04.26
 */
@Order(CO_CONCRETE)
@Component
public class StartCommand extends BotCommand {

    private final ReplyKeyboard replyKeyboard;

    public StartCommand(KeyboardFactory keyboardFactory) {
        this.replyKeyboard = keyboardFactory.createKeyboardFromEnum(Command.values());
    }

    @Override
    protected ReplyKeyboard getReplyKeyboard() {
        return replyKeyboard;
    }

    @Override
    protected Command getCommand() {
        return Command.START;
    }

}
