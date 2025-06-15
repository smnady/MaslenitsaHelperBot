package com.itc.maslenitsabot.keyboard;

import com.itc.maslenitsabot.common.meta.Descriable;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

/**
 * Интерфейс фабрики создания экранной клавиатуры.
 *
 * @author smnadya
 * @since 2025.04.26
 */
public interface KeyboardFactory {

    ReplyKeyboard createKeyboardFromEnum(Descriable[] values);

}
