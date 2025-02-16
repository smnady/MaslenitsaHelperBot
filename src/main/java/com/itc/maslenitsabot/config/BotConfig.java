package com.itc.maslenitsabot.config;

import com.itc.maslenitsabot.common.Recipe;
import com.itc.maslenitsabot.common.Station;
import com.itc.maslenitsabot.config.security.BotAuthProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Настройки конфигурации бота.
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
     * Путь к директории с файлами с информацией о рецептах.
     */
    private String contentPathRecipes;

    /**
     * Путь к директории с файлами с информацией о станциях.
     */
    private String contentPathStations;

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

    public String getContentPathRecipes() {
        return contentPathRecipes;
    }

    public void setContentPathRecipes(String contentPathRecipes) {
        this.contentPathRecipes = contentPathRecipes;
    }

    public String getContentPathStations() {
        return contentPathStations;
    }

    public void setContentPathStations(String contentPathStations) {
        this.contentPathStations = contentPathStations;
    }

    @PostConstruct
    public void init() {
        Recipe.setPathToDir(contentPathRecipes);
        Station.setPathToDir(contentPathStations);
    }

}
