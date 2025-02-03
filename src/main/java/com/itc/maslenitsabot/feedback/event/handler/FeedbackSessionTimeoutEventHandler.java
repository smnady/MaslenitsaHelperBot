package com.itc.maslenitsabot.feedback.event.handler;

import com.itc.maslenitsabot.TelegramBot;
import com.itc.maslenitsabot.feedback.event.FeedbackSessionTimeoutEvent;
import com.itc.maslenitsabot.user.BotUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Обработчик истечения времени сессии обратной связи.
 *
 * @author smnadya
 * @since 2025.02.02
 */
@Component
public class FeedbackSessionTimeoutEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackSessionTimeoutEventHandler.class);

    private static final String MESSAGE = """
            Ваша сессия обратной связи истекла.
                        
            Пожалуйста, начните ее снова, чтобы мы смогли получить ваш отзыв и стать лучше 🚀
            """;

    private final TelegramBot bot;

    @Autowired
    public FeedbackSessionTimeoutEventHandler(TelegramBot bot) {
        this.bot = bot;
    }

    @EventListener
    public void onFeedbackSessionTimeout(FeedbackSessionTimeoutEvent event) {
        BotUser addressee = event.user();
        bot.sendMessage(addressee.getTelegramUserId(), MESSAGE);

        LOGGER.info("Отправлено уведомление пользователю {} о том, что его сессия обратной связи истекла.", addressee.getId());
    }

}
