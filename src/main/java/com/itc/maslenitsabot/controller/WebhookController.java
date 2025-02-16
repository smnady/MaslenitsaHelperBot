package com.itc.maslenitsabot.controller;

import com.itc.maslenitsabot.TelegramBot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * Обрабатывает запросы пользователей, отправляемые через бота.
 *
 * @author smnadya
 * @since 2025.01.28
 */
@Controller
@RequestMapping("/")
public class WebhookController {

    private final TelegramBot telegramBot;

    public WebhookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping
    @ResponseBody
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping
    public String goToAdminPage() {
        return "redirect:/admin/dashboard";
    }

}
