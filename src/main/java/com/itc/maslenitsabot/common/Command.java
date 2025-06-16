package com.itc.maslenitsabot.common;

import com.itc.maslenitsabot.common.meta.Descriable;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import static com.itc.maslenitsabot.common.Command.Message.CHOOSE_RECIPE;
import static com.itc.maslenitsabot.common.Command.Message.CHOOSE_STATION;
import static com.itc.maslenitsabot.common.Command.Message.CONGRATULATION_TEXT;
import static com.itc.maslenitsabot.common.Command.Message.GREETINGS;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_FIRST_MESSAGE;

/**
 * Команды, обрабатываевыемые ботом.
 *
 * @author smnadya
 * @since 2025.01.25
 */
public enum Command implements Descriable {

    START("/start", "Начало", GREETINGS),
    CONGRATULATION("/congratulation", "Поздравление", CONGRATULATION_TEXT),
    RECIPES("/recipes", "Рецепты блинов", CHOOSE_RECIPE),
    STATIONS("/stations", "Станции", CHOOSE_STATION),
    FEEDBACK("/feedback", "Обратная связь", FQ_FIRST_MESSAGE);

    private final String key;
    private final String description;
    private final String returnedValue;

    Command(String key, String description, String returnedValue) {
        this.key = key;
        this.description = description;
        this.returnedValue = returnedValue;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public String getReturnedValue() {
        return returnedValue;
    }

    public boolean isCommand(String actualText) {
        return this.getKey().equals(actualText) || this.getDescription().equals(actualText);
    }

    public BotCommand toBotCommand() {
        return new BotCommand(this.getKey(), this.getDescription());
    }

    public static final class Message {

        public static final String GREETINGS = """
                Привет! 🎉
                
                Добро пожаловать в Масленичный Помощник! 🥞
                
                Я здесь, чтобы помочь вам сделать празднование Масленицы незабываемым! Вы найдете различные рецепты блинов, а также сможете узнать все об играх на Масленицу и их точное описание.
                
                Давайте вместе отпразднуем этот веселый и радостный праздник!
                
                Начнем? ✨
                """;

        public static final String CONGRATULATION_TEXT = """
                🎉 С Масленицей, дорогие друзья! 🎉
                
                Поздравляем вас с началом веселого и яркого праздника! Пусть этот замечательный период принесет вам радость, тепло и уют! 🥞
                
                Желаем, чтобы ваши блины были всегда вкусными, а начинка — разнообразной! Пусть в ваших домах царит атмосфера дружбы и веселья, а сердца наполняются счастьем и любовью.
                
                Не забывайте делиться с нами своими кулинарными шедеврами и праздничными моментами! Пусть Масленица станет для вас временем радости и новых свершений! 🌼✨
                
                С любовью, ваш Масленичный Помощник! ❤
                """;

        public static final String CHOOSE_RECIPE = "Выберите рецепт для просмотра 🥞";

        public static final String CHOOSE_STATION = "Выберите станцию для просмотра ✨";

        public static final String DONT_UNDERSTAND = "Ничего не понятно, но очень интересно. Возможно, в скором будущем я смогу тебя понять\uD83E\uDD29";

        public static final String ROLLBACK_BUTTON = "Назад ↩";

        public static final String ROLLBACK_ANSWER = "Вы вернулись в основное меню";

        public static final String CONTINUE_FEEDBACK_MESSAGE = "Чтобы продолжить, завершите прохождение опроса:)";

        public static final String FEEDBACK_MESSAGE_SHOULD_NOT_BE_A_COMMAND = """
                Комментарий обратной связи не должен совпадать по содержанию с какой-либо командой.
                
                Попробуйте снова ответить на вопрос✨
                """;

    }

}
