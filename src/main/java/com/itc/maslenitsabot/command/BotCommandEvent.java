package com.itc.maslenitsabot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Событие, представляющее команду бота.
 * <p>
 * Используется для передачи данных, необходимых для обработки команды.
 * </p>
 *
 * @param prompt      текст ввода, предоставленный пользователем
 * @param sendMessage объект для отправки ответа пользователю
 * @param message     объект, представляющий исходное сообщение, полученное ботом
 * @author smnadya
 * @since 2025.06.15
 */
public record BotCommandEvent(String prompt,
                              SendMessage sendMessage,
                              Message message) {

}
