package com.itc.maslenitsabot;

import com.itc.maslenitsabot.common.Command;
import com.itc.maslenitsabot.common.ContentLoader;
import com.itc.maslenitsabot.common.Recipe;
import com.itc.maslenitsabot.common.Station;
import com.itc.maslenitsabot.common.meta.Descriable;
import com.itc.maslenitsabot.common.meta.HavingOuterContent;
import com.itc.maslenitsabot.config.BotConfig;
import com.itc.maslenitsabot.feedback.Feedback;
import com.itc.maslenitsabot.feedback.FeedbackService;
import com.itc.maslenitsabot.user.BotUser;
import com.itc.maslenitsabot.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.itc.maslenitsabot.common.Command.Message.CONTINUE_FEEDBACK_MESSAGE;
import static com.itc.maslenitsabot.common.Command.Message.FEEDBACK_MESSAGE_SHOULD_NOT_BE_A_COMMAND;
import static com.itc.maslenitsabot.common.Command.Message.ROLLBACK_ANSWER;
import static com.itc.maslenitsabot.common.Command.Message.ROLLBACK_BUTTON;
import static com.itc.maslenitsabot.common.Command.Message.getDontUnderstandMessage;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_FIRST_CALLBACK_VALUE;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_FIRST_MESSAGE;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_SECOND_CALLBACK_VALUE;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_SECOND_MESSAGE;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_THANKS;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_THIRD_MESSAGE;

/**
 * Основная логика обработки действий пользователя ботом.
 *
 * @author smnadya
 * @since 2025.01.25
 */
@Component
public class TelegramBot extends TelegramWebhookBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBot.class);

    private final FeedbackService feedbackService;

    private final UserService userService;

    private final BotConfig config;

    public TelegramBot(FeedbackService feedbackService, UserService userService, BotConfig config) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.config = config;
        createMenu();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            var message = update.getMessage();
            var botUser = userService.findOrSaveBotUser(message.getFrom());
            LOGGER.info("Пришло сообщение от {}", botUser);
            final Feedback feedbackSession = feedbackService.getFeedbackSession(botUser);
            if (feedbackSession != null) {
                String messageText;
                if (feedbackSession.getStep() != 3) {
                    messageText = CONTINUE_FEEDBACK_MESSAGE;
                } else if (!FeedbackService.getBlackList().contains(message.getText())) {
                    feedbackService.updateFeedbackSession(feedbackSession, null, null, message.getText());
                    feedbackService.saveFeedbackSession(feedbackSession);
                    messageText = FQ_THANKS;
                } else {
                    messageText = FEEDBACK_MESSAGE_SHOULD_NOT_BE_A_COMMAND;
                }
                sendMessage(message.getChatId(), messageText);
                return null;
            }
            processMessage(update.getMessage(), botUser);
        }
        return null;
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        BotUser user = userService.findOrSaveBotUser(callbackQuery.getFrom());
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String data = callbackQuery.getData();
        Feedback feedback = feedbackService.getFeedbackSession(user);

        if (feedback == null) return;

        if (data.startsWith(FQ_FIRST_CALLBACK_VALUE)) {
            feedbackService.updateFeedbackSession(feedback, Integer.parseInt(data.split("_")[2]), null, null);
            sendQuestion(chatId, messageId, FQ_SECOND_MESSAGE, FQ_SECOND_CALLBACK_VALUE);
        } else if (data.startsWith(FQ_SECOND_CALLBACK_VALUE)) {
            feedbackService.updateFeedbackSession(feedback, null, Integer.parseInt(data.split("_")[2]), null);
            sendTextFeedbackQuestion(chatId, messageId, FQ_THIRD_MESSAGE);
        }
    }

    private void processMessage(Message message, BotUser user) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        if (message.hasText()) {
            if (!setupKeyboardAndPrepareAnswer(sendMessage, message.getText())) {
                feedbackService.startFeedbackSession(user);
                sendQuestion(message.getChatId(), null, FQ_FIRST_MESSAGE, FQ_FIRST_CALLBACK_VALUE);
                return;
            }
            sendMessage.setParseMode(ParseMode.HTML);
        } else {
            setupKeyboardAndPrepareAnswer(sendMessage, getDontUnderstandMessage());
        }
        sendMessage(sendMessage);
    }

    private void sendQuestion(Long chatId, Integer messageId, String question, String prefix) {
        InlineKeyboardMarkup markup = createInlineKeyboardForFQ(prefix);
        if (messageId == null) {
            SendMessage message = new SendMessage(chatId.toString(), question);
            message.setReplyMarkup(markup);
            sendMessage(message);
            return;
        }
        EditMessageText editMessage = EditMessageText.builder().chatId(chatId.toString())
                .messageId(messageId).text(question).replyMarkup(markup).build();
        sendMessage(editMessage);
    }

    private InlineKeyboardMarkup createInlineKeyboardForFQ(String prefix) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineButtons = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            InlineKeyboardButton button = new InlineKeyboardButton(String.valueOf(i));
            button.setCallbackData(prefix + "_" + i);
            inlineButtons.add(button);
        }
        buttons.add(inlineButtons);
        markup.setKeyboard(buttons);
        return markup;
    }

    private void sendTextFeedbackQuestion(Long chatId, Integer messageId, String question) {
        EditMessageText editMessage = EditMessageText.builder().chatId(chatId.toString())
                .messageId(messageId).text(question).build();
        sendMessage(editMessage);
    }

    public void sendMessage(Long chatId, String text) {
        sendMessage(new SendMessage(chatId.toString(), text));
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> void sendMessage(Method method) {
        try {
            execute(method);
        } catch (TelegramApiException e) {
            LOGGER.error("Произошла ошибка при попытке выполнить метод: {}", method, e);
            throw new RuntimeException(e);
        }
    }

    private boolean setupKeyboardAndPrepareAnswer(SendMessage message, String query) {
        Command command = Command.recognizeCommand(query);
        if (command == null) {
            if (processSubcommand(message, query)) return true;
            setupKeyboard(message, Command.values());
            String answer = ROLLBACK_BUTTON.equals(query) ? ROLLBACK_ANSWER : getDontUnderstandMessage();
            message.setText(answer);
            return true;
        }
        switch (command) {
            case FEEDBACK -> {
                return false;
            }
            case START, CONGRATULATION -> setupKeyboard(message, Command.values());
            case RECIPES -> setupKeyboard(message, Recipe.values());
            case STATIONS -> setupKeyboard(message, Station.values());
        }
        message.setText(command.getReturnedValue());
        return true;
    }

    private boolean processSubcommand(SendMessage message, String query) {
        if (processEntity(message, query, Recipe.values())) {
            return true;
        }
        return processEntity(message, query, Station.values());
    }

    private <T extends Descriable & HavingOuterContent> boolean processEntity(
            SendMessage message, String query, T[] values) {
        T entity = valueByDescription(values, query);
        if (entity != null) {
            setupKeyboard(message, values);
            message.setText(ContentLoader.getRepresentation(entity));
            return true;
        }
        return false;
    }

    private <T extends Descriable & HavingOuterContent> T valueByDescription(T[] values, String description) {
        return Stream.of(values)
                .filter(entity -> entity.getDescription().equals(description))
                .findAny()
                .orElse(null);
    }

    private void setupKeyboard(SendMessage message, Descriable[] values) {
        ReplyKeyboardMarkup virtualKeyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>(values.length + 2);
        Stream.of(values).forEach(action -> rows.add(createButton(action.getDescription())));
        if (values instanceof Recipe[] || values instanceof Station[]) {
            rows.add(createButton(ROLLBACK_BUTTON));
        }
        virtualKeyboard.setKeyboard(rows);
        message.setReplyMarkup(virtualKeyboard);
    }

    private KeyboardRow createButton(String description) {
        return new KeyboardRow(Collections.singletonList(new KeyboardButton(description)));
    }

    private void createMenu() {
        try {
            execute(
                    SetMyCommands.builder()
                            .commands(Stream.of(Command.values()).map(Command::toBotCommand).toList())
                            .scope(new BotCommandScopeDefault())
                            .languageCode(null).build()
            );
        } catch (TelegramApiException e) {
            LOGGER.error("Произошла ошибка при попытке создать начальное меню с командами", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotPath() {
        return config.getWebhookUrl();
    }

}
