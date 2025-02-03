package com.itc.maslenitsabot.config;

import com.itc.maslenitsabot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Устанавливает вебхук бота.
 *
 * @author smnadya
 * @since 2025.01.25
 */
@Component
@SuppressWarnings("all")
public class BotInitializer {

    @Autowired
    private TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            SetWebhook setWebhook = SetWebhook.builder().url(bot.getBotPath()).build();
            bot.execute(setWebhook);

        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка при регистрации вебхука", e);
        }
    }

}
