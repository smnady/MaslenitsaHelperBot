package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.command.BotCommand;
import com.itc.maslenitsabot.common.Command;
import com.itc.maslenitsabot.keyboard.KeyboardFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static com.itc.maslenitsabot.command.CommandOrder.CO_CONCRETE;

/**
 * Команда для отправки поздравлений пользователям.<br/>
 * Использует клавиатуру, созданную на основе перечисления {@link Command}.
 *
 * @author smnadya
 * @since 2025.04.26
 */
@Order(CO_CONCRETE)
@Component
public class CongratulationCommand extends BotCommand {

    private final ReplyKeyboard replyKeyboard;

    public CongratulationCommand(KeyboardFactory keyboardFactory) {
        replyKeyboard = keyboardFactory.createKeyboardFromEnum(Command.values());
    }

    @Override
    protected ReplyKeyboard getReplyKeyboard() {
        return replyKeyboard;
    }

    @Override
    protected Command getCommand() {
        return Command.CONGRATULATION;
    }

}
