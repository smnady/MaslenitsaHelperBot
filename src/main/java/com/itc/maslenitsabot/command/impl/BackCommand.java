package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.command.BotCommand;
import com.itc.maslenitsabot.common.Command;
import com.itc.maslenitsabot.keyboard.KeyboardFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Objects;

import static com.itc.maslenitsabot.command.CommandOrder.CO_CONCRETE;
import static com.itc.maslenitsabot.common.Command.Message.ROLLBACK_ANSWER;
import static com.itc.maslenitsabot.common.Command.Message.ROLLBACK_BUTTON;

/**
 * Класс является конкретной реализацией абстрактного класса {@link BotCommand}.<br/>
 * Он обрабатывает пользовательскую команду "Назад" (откат), возвращая пользователя
 * в главное меню бота.
 *
 * <p>Класс использует клавиатуру ответа, созданную через {@link KeyboardFactory}, чтобы
 * предоставить варианты на основе доступных команд.</p>
 *
 * <p>Команда активируется, когда ввод пользователя совпадает с заранее заданным
 * текстом кнопки отката.<br/>
 * Команда "Назад" не сопоставляется с конкретной {@link Command}, поэтому при
 * попытке получить связанную с ней команду выбрасывается
 * {@link UnsupportedOperationException}.</p>
 *
 * <p><b>Основные функции:</b><br/>
 * - Определяет клавиатуру, отображаемую для команды "Назад".<br/>
 * - Проверяет, соответствует ли введенная пользователем команда тексту кнопки для отката.<br/>
 * - Предоставляет ответное сообщение при выполнении команды.</p>
 *
 * <p>Экземпляр {@link KeyboardFactory} используется для инициализации {@link ReplyKeyboard} со всеми
 * доступными командами.</p>
 *
 * @author smnadya
 * @since 2025.06.15
 */
@Component
@Order(CO_CONCRETE)
public class BackCommand extends BotCommand {

    private final ReplyKeyboard replyKeyboard;

    public BackCommand(KeyboardFactory keyboardFactory) {
        this.replyKeyboard = keyboardFactory.createKeyboardFromEnum(Command.values());
    }

    @Override
    protected ReplyKeyboard getReplyKeyboard() {
        return replyKeyboard;
    }

    @Override
    public boolean canExecute(String userCommand) {
        return Objects.equals(ROLLBACK_BUTTON, userCommand);
    }

    @Override
    protected String getAnswerMessage() {
        return ROLLBACK_ANSWER;
    }

    @Override
    protected Command getCommand() {
        throw new UnsupportedOperationException();
    }

}
