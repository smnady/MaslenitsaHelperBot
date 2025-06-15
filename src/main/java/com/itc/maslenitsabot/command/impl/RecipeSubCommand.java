package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.common.ContentLoader;
import com.itc.maslenitsabot.common.Recipe;
import com.itc.maslenitsabot.common.meta.Descriable;
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
 * Расширение {@link RecipesCommand}, используемая для обработки подкоманд, связанных с рецептами.
 * Эта подкоманда обрабатывает ввод пользователя и предоставляет соответствующие ответы на основе доступных рецептов.
 * <br/>
 * {@code RecipeSubCommand} полагается на перечисления {@link Recipe} и метод {@link ContentLoader#getRepresentation(Descriable)}
 * для получения деталей конкретного рецепта и их отображения для пользователя.
 * <br/>
 * Переопределяет методы для определения способности выполнить команду, настройки клавиатуры для ответа,
 * а также исполнения связанных действий на основе пользовательского ввода.
 * <br/>
 * Исключение {@link UnsupportedOperationException} выбрасывается при вызове метода {@code getAnswerMessage()},
 * поскольку он не используется в данной реализации.
 * <br/>
 * Исключение также будет выброшено во время выполнения, если предоставленный пользовательский ввод
 * не соответствует ни одному из доступных рецептов {@link Recipe}.
 */
@Component
@Order(CO_CONCRETE_SUBCOMAND)
public class RecipeSubCommand extends RecipesCommand {

    public RecipeSubCommand(KeyboardFactory keyboardFactory) {
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
                        Arrays.stream(Recipe.values())
                                .filter(recipe -> Objects.equals(recipe.getDescription(), prompt))
                                .findFirst()
                                .orElseThrow(IllegalStateException::new)
                )
        );
        message.setParseMode(ParseMode.HTML);
    }

    @Override
    public boolean canExecute(String command) {
        return Arrays.stream(Recipe.values())
                .anyMatch(recipe -> Objects.equals(recipe.getDescription(), command));
    }

}
