package com.itc.maslenitsabot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Сервис для работы с пользователями бота.
 *
 * @author smnadya
 * @since 2025.01.25
 */
@Service
public class UserService {

    private static final String DONT_EXIST = "-";

    private final BotUserDAO userDAO;

    @Autowired
    public UserService(BotUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public BotUser findOrSaveBotUser(User telegramUser) {
        BotUser persistentBotUser = userDAO.findBotUserByTelegramUserId(telegramUser.getId());
        if (persistentBotUser == null) {
            var lastNameOrDefault = telegramUser.getLastName() != null ? telegramUser.getLastName() : "";
            var usernameOrDefault = telegramUser.getUserName() != null ? telegramUser.getUserName() : DONT_EXIST;
            BotUser transientBotUser = BotUser.builder().telegramUserId(telegramUser.getId())
                    .firstName(telegramUser.getFirstName())
                    .lastName(lastNameOrDefault)
                    .username(usernameOrDefault)
                    .build();
            return userDAO.save(transientBotUser);
        }
        return persistentBotUser;
    }

}
