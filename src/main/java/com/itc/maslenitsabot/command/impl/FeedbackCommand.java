package com.itc.maslenitsabot.command.impl;

import com.itc.maslenitsabot.command.BotCommand;
import com.itc.maslenitsabot.common.Command;
import com.itc.maslenitsabot.feedback.FeedbackService;
import com.itc.maslenitsabot.user.UserService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.itc.maslenitsabot.command.CommandOrder.CO_CONCRETE;
import static com.itc.maslenitsabot.feedback.Feedback.Question.FQ_FIRST_CALLBACK_VALUE;

/**
 * Команда для сбора обратной связи от пользователей.
 * <p>
 * Реализует функциональность начала сеанса сбора обратной связи,
 * отправки сообщения с клавиатурой выбора варианта ответа и сохранения пользователя,
 * если он ранее не был зарегистрирован в системе.
 * </p>
 *
 * @author smnadya
 * @since 2025.04.26
 */
@Order(CO_CONCRETE)
@Component
public class FeedbackCommand extends BotCommand {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final ReplyKeyboard replyKeyboard;

    public FeedbackCommand(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        replyKeyboard = createReplyKeyboard();
    }

    @Override
    public void execute(String prompt, SendMessage sendMessage, Message message) {
        var botUser = userService.findOrSaveBotUser(message.getFrom());

        feedbackService.startFeedbackSession(botUser);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(getAnswerMessage());
        sendMessage.setReplyMarkup(replyKeyboard);
    }

    @Override
    protected ReplyKeyboard getReplyKeyboard() {
        return replyKeyboard;
    }

    @Override
    protected Command getCommand() {
        return Command.FEEDBACK;
    }

    private ReplyKeyboard createReplyKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.valueOf(i));
            button.setCallbackData(FQ_FIRST_CALLBACK_VALUE + "_" + i);
            buttonsRow.add(button);
        }
        rows.add(buttonsRow);
        markup.setKeyboard(rows);

        return markup;
    }

}
