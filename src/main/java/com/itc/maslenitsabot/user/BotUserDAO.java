package com.itc.maslenitsabot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO пользователей бота.
 *
 * @author smnadya
 * @since 2025.01.28
 */
@Repository
public interface BotUserDAO extends JpaRepository<BotUser, Long> {

    BotUser findBotUserByTelegramUserId(Long id);

}
