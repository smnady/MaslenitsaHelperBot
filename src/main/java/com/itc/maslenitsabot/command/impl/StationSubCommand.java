package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.common.ContentLoader;
import com.itc.maslenitsabot.common.Station;
import com.itc.maslenitsabot.keyboard.KeyboardFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.Objects;

import static com.itc.maslenitsabot.command.CommandOrder.CO_CONCRETE_SUBCOMAND;

/**
 * Подкоманда для работы с конкретными станциями.
 * <p>
 * Предназначена для обработки пользовательских запросов, связанных с выбором или отображением информации о станциях,
 * на основе данных перечисления {@link Station}.
 * </p>
 *
 * @author smnadya
 * @since 2025.06.15
 */
@Component
@Order(CO_CONCRETE_SUBCOMAND)
public class StationSubCommand extends StationsCommand {

    public StationSubCommand(KeyboardFactory keyboardFactory) {
        super(keyboardFactory);
    }

    @Override
    protected String getAnswerMessage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void execute(String prompt, SendMessage message, Message botMessage) {
        message.setReplyMarkup(getReplyKeyboard());
        message.setText(
                ContentLoader.getRepresentation(
                        Arrays.stream(Station.values())
                                .filter(station -> Objects.equals(station.getDescription(), prompt))
                                .findFirst()
                                .orElseThrow(IllegalStateException::new)
                )
        );
        message.setParseMode(ParseMode.HTML);
    }

    @Override
    public boolean canExecute(String command) {
        return Arrays.stream(Station.values())
                .anyMatch(station -> Objects.equals(station.getDescription(), command));
    }

}
