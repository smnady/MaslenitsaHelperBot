package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.command.BotCommand;
import com.itc.maslenitsabot.common.Command;
import com.itc.maslenitsabot.common.Station;
import com.itc.maslenitsabot.keyboard.KeyboardFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static com.itc.maslenitsabot.command.CommandOrder.CO_CONCRETE;
import static com.itc.maslenitsabot.common.Command.STATIONS;

/**
 * Команда для работы со списком станций.
 * <p>
 * Использует клавиатуру, созданную на основе перечисления {@link Station}.
 * </p>
 *
 * @author smnadya
 * @since 2025.04.26
 */
@Order(CO_CONCRETE)
@Component
public class StationsCommand extends BotCommand {

    private final ReplyKeyboard replyKeyboard;

    public StationsCommand(KeyboardFactory keyboardFactory) {
        this.replyKeyboard = keyboardFactory.createKeyboardFromEnum(Station.values());
    }

    @Override
    protected ReplyKeyboard getReplyKeyboard() {
        return replyKeyboard;
    }

    @Override
    protected Command getCommand() {
        return STATIONS;
    }

}
