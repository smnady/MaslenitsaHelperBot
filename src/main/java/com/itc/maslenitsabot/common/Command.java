package com.itc.maslenitsabot.common;

import com.itc.maslenitsabot.common.meta.Descriable;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.stream.Stream;

import static com.itc.maslenitsabot.common.Command.Message.CHOOSE_RECIPE;
import static com.itc.maslenitsabot.common.Command.Message.CHOOSE_STATION;
import static com.itc.maslenitsabot.common.Command.Message.CONGRATULATION_TEXT;
import static com.itc.maslenitsabot.common.Command.Message.GREETINGS;

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
    FEEDBACK("/feedback", "Обратная связь", "Насколько вам понравилось мероприятие?");

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

    public static Command recognizeCommand(String message) {
        return Stream.of(Command.values())
                .filter(command -> command.getKey().equals(message) || command.getDescription().equals(message))
                .findAny()
                .orElse(null);
    }

    public BotCommand toBotCommand() {
        return new BotCommand(this.getKey(), this.getDescription());
    }

    public static final class Message {

        static final String GREETINGS = """
                Привет! 🎉
                            
                Добро пожаловать в Масленичный Помощник! 🥞
                            
                Я здесь, чтобы помочь вам сделать празднование Масленицы незабываемым! Вы найдете различные рецепты блинов, а также сможете узнать все об играх на Масленицу и их точное описание.
                            
                Давайте вместе отпразднуем этот веселый и радостный праздник!
                    
                Начнем? ✨
                """;

        static final String CONGRATULATION_TEXT = """
                🎉 С Масленицей, дорогие друзья! 🎉
                            
                Поздравляем вас с началом веселого и яркого праздника! Пусть этот замечательный период принесет вам радость, тепло и уют! 🥞
                    
                Желаем, чтобы ваши блины были всегда вкусными, а начинка — разнообразной! Пусть в ваших домах царит атмосфера дружбы и веселья, а сердца наполняются счастьем и любовью.
                            
                Не забывайте делиться с нами своими кулинарными шедеврами и праздничными моментами! Пусть Масленица станет для вас временем радости и новых свершений! 🌼✨
                            
                С любовью, ваш Масленичный Помощник! ❤
                """;

        static final String CHOOSE_RECIPE = "Выберите рецепт для просмотра 🥞";

        static final String CHOOSE_STATION = "Выберите станцию для просмотра ✨";

        static final String DONT_UNDERSTAND = "Ничего не понятно, но очень интересно. Возможно, в скором будущем я смогу тебя понять\uD83E\uDD29";

        public static final String ROLLBACK_BUTTON = "Назад ↩";

        public static final String ROLLBACK_ANSWER = "Вы вернулись в основное меню";

        public static final String CONTINUE_FEEDBACK_MESSAGE = "Чтобы продолжить, завершите прохождение опроса:)";

        public static final String FEEDBACK_MESSAGE_SHOULD_NOT_BE_A_COMMAND = """
                Комментарий обратной связи не должен совпадать по содержанию с какой-либо командой.
                                
                Попробуйте снова ответить на вопрос✨
                """;

        public static String getDontUnderstandMessage() {
            return DONT_UNDERSTAND;
        }

    }
}
