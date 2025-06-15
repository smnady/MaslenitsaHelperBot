package com.itc.maslenitsabot.keyboard.impl;

import com.itc.maslenitsabot.common.Recipe;
import com.itc.maslenitsabot.common.Station;
import com.itc.maslenitsabot.common.meta.Descriable;
import com.itc.maslenitsabot.keyboard.KeyboardFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.itc.maslenitsabot.common.Command.Message.ROLLBACK_BUTTON;

/**
 * Реализация фабрики создания экранной клавиатуры.
 *
 * @author smnadya
 * @since 2025.04.26
 */
@Component
public class ReplyKeyboardFactoryImpl implements KeyboardFactory {

    /**
     * Создать экранную клавиатуру.
     *
     * @param values список описываемых сущностей, которые будут отражены на клавиатуре
     * @return экранная клавиатура
     */
    @Override
    public ReplyKeyboardMarkup createKeyboardFromEnum(Descriable[] values) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        Stream.of(values)
                .forEach(value -> rows.add(createButton(value.getDescription())));

        if (values instanceof Recipe[] || values instanceof Station[]) {
            rows.add(createButton(ROLLBACK_BUTTON));
        }

        keyboard.setKeyboard(rows);
        return keyboard;
    }

    private static KeyboardRow createButton(String text) {
        return new KeyboardRow(Collections.singletonList(new KeyboardButton(text)));
    }

}
