package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.command.BotCommand;
import com.itc.maslenitsabot.common.Command;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static com.itc.maslenitsabot.command.CommandOrder.CO_COMMON;
import static com.itc.maslenitsabot.common.Command.Message.DONT_UNDERSTAND;

/**
 * Команда для обработки неизвестных или некорректных запросов, отправленных пользователем.
 * <p>
 * Эта команда используется, когда введённая команда не соответствует ни одной из существующих
 * или поддерживаемых ботом.
 * </p>
 *
 * @author smnadya
 * @since 2025.04.26
 */
@Order(CO_COMMON)
@Component
public class UnknownCommand extends BotCommand {

    @Override
    public void execute(String prompt, SendMessage message, Message botMessage) {
        message.setText(getAnswerMessage());
    }

    @Override
    public boolean canExecute(String command) {
        return true;
    }

    @Override
    protected String getAnswerMessage() {
        return DONT_UNDERSTAND;
    }

    @Override
    protected ReplyKeyboard getReplyKeyboard() {
        throw new UnsupportedOperationException("Unknown command doesn't set new ReplyKeyboard");
    }

    @Override
    protected Command getCommand() {
        throw new UnsupportedOperationException("Unknown command doesn't have the enum type of Command");
    }

}
