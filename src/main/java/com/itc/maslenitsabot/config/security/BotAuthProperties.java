package com.itc.maslenitsabot.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import static com.itc.maslenitsabot.config.security.BotAuthProperties.BAP_PREFIX;

/**
 * Свойства для аутентификации в админке.
 *
 * @author smnadya
 * @since 2025.02.02
 */
@ConfigurationProperties(BAP_PREFIX)
@PropertySource("classpath:/application.properties")
public class BotAuthProperties {

    /**
     * Префикс свойств конфигурации.
     */
    public static final String BAP_PREFIX = "maslenitsa.bot.admin";

    /**
     * Логин админ-пользователя.
     */
    private String username;

    /**
     * Пароль админ-пользователя.
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
