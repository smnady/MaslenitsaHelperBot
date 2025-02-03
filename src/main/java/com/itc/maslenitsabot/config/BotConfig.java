package com.itc.maslenitsabot.config;

import com.itc.maslenitsabot.config.security.BotAuthProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Настройки конфигрурации бота.
 *
 * @author smnadya
 * @since 2025.01.25
 */
@Configuration
@ConfigurationProperties(BotConfig.BC_PREFIX)
@EnableConfigurationProperties({BotAuthProperties.class})
public class BotConfig {

    /**
     * Префикс свойств конфигурации.
     */
    public static final String BC_PREFIX = "maslenitsa.bot.config";

    /**
     * Наименование бота (задаётся в {@code @BotFather}).
     */
    private String name;

    /**
     * Токен для доступа к api бота (также берется из {@code @BotFather}).
     */
    private String token;

    /**
     * Адрес вебхука.
     */
    private String webhookUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

}
