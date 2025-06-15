package com.itc.maslenitsabot.command;

import com.itc.maslenitsabot.common.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

/**
 * Абстрактный класс, представляющий команду бота.
 * <p>
 * Предоставляет общий интерфейс и базовую реализацию для взаимодействия с командами.
 * </p>
 *
 * @author smnadya
 * @since 2025.04.26
 */
public abstract class BotCommand {

    public void execute(String prompt, SendMessage message, Message botMessage) {
        message.setText(getAnswerMessage());
        message.setReplyMarkup(getReplyKeyboard());
    }

    public boolean canExecute(String userCommand) {
        return getCommand().isCommand(userCommand);
    }

    protected abstract ReplyKeyboard getReplyKeyboard();

    protected abstract Command getCommand();

    protected String getAnswerMessage() {
        return getCommand().getReturnedValue();
    }

}
