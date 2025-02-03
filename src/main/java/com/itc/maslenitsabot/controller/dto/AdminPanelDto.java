package com.itc.maslenitsabot.controller.dto;

import com.itc.maslenitsabot.user.BotUser;
import com.itc.maslenitsabot.feedback.Feedback;

import java.util.List;

/**
 * Dto для админ-страницы.
 *
 * @param feedbacks             спискок из обратной связи
 * @param users                 пользователи
 * @param averageEventRating    средняя оценка мероприятия
 * @param averagePancakesRating средняя оценка блинов
 * @author smnadya
 * @since 2025.02.02
 */
public record AdminPanelDto(List<Feedback> feedbacks,
                            List<BotUser> users,
                            double averageEventRating,
                            double averagePancakesRating) {

    public AdminPanelDto(List<Feedback> feedbacks, List<BotUser> users) {
        this(feedbacks, users, calculateAverageEventRating(feedbacks), calculateAveragePancakesRating(feedbacks));
    }

    /**
     * <b>Важно:</b> Все аргументы нужны для переопределения дефолтного коструктора рекорда.</p>
     *
     * @param feedbacks             список обратной связи пользователей
     * @param users                 список пользователей
     * @param averageEventRating    не используется, так как вычисляется на основе {@link #feedbacks}
     * @param averagePancakesRating не используется, так как вычисляется на основе {@link #feedbacks}
     * @deprecated Используйте конструктор с двумя аргументами
     */
    public AdminPanelDto(List<Feedback> feedbacks,
                         List<BotUser> users,
                         double averageEventRating,
                         double averagePancakesRating) {
        this.feedbacks = feedbacks;
        this.users = users;
        this.averageEventRating = calculateAverageEventRating(feedbacks);
        this.averagePancakesRating = calculateAveragePancakesRating(feedbacks);
    }

    private static double calculateAverageEventRating(List<Feedback> feedbacks) {
        if (feedbacks == null) return 0.0;
        return feedbacks.stream()
                .mapToInt(Feedback::getEventRating)
                .average()
                .orElse(0.0);
    }

    private static double calculateAveragePancakesRating(List<Feedback> feedbacks) {
        if (feedbacks == null) return 0.0;
        return feedbacks.stream()
                .mapToInt(Feedback::getPancakesRating)
                .average()
                .orElse(0.0);
    }

}
