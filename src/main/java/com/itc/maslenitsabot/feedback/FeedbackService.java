package com.itc.maslenitsabot.feedback;

import com.itc.maslenitsabot.common.Command;
import com.itc.maslenitsabot.common.Recipe;
import com.itc.maslenitsabot.common.Station;
import com.itc.maslenitsabot.feedback.event.FeedbackSessionTimeoutEvent;
import com.itc.maslenitsabot.user.BotUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Сервис для проведения сессии опроса обратной связи.
 *
 * @author smnadya
 * @since 2025.01.28
 */
@Service
public class FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

    private static final Set<String> BLACK_LIST = Stream.concat(
            Stream.concat(
                    Stream.of(Command.values()).map(Command::getKey),
                    Stream.of(Command.values()).map(Command::getDescription)),
            Stream.concat(
                    Stream.of(Recipe.values()).map(Recipe::getDescription),
                    Stream.of(Station.values()).map(Station::getDescription)
            )).collect(Collectors.toUnmodifiableSet());

    private static final int TIMEOUT = 10;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final Map<String, Feedback> feedbackSessions = new ConcurrentHashMap<>();

    private final ApplicationEventPublisher eventPublisher;

    private final FeedbackDAO feedbackDAO;

    @Autowired
    public FeedbackService(ApplicationEventPublisher eventPublisher, FeedbackDAO feedbackDAO) {
        this.eventPublisher = eventPublisher;
        this.feedbackDAO = feedbackDAO;
        startCleanupTask();
    }

    public void startFeedbackSession(BotUser user) {
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setStep(1);
        feedbackSessions.put(user.getId(), feedback);
        LOGGER.info("Feedback session started: {}", feedback);
    }


    public Feedback getFeedbackSession(BotUser user) {
        return feedbackSessions.get(user.getId());
    }

    public void updateFeedbackSession(Feedback feedback, Integer eventRating, Integer pancakesRating, String comment) {
        if (eventRating != null) feedback.setEventRating(eventRating);
        if (pancakesRating != null) feedback.setPancakesRating(pancakesRating);
        if (comment != null) feedback.setComment(comment);
        feedback.setStep(feedback.getStep() + 1);
        feedback.setLastUpdated(LocalDateTime.now());
        LOGGER.info("Feedback session updated: {}", feedback);
    }

    @Transactional
    public void saveFeedbackSession(Feedback feedback) {
        if (feedback == null) {
            return;
        }
        feedbackSessions.remove(feedback.getUser().getId());
        feedbackDAO.save(feedback);
        LOGGER.info("Feedback session completed and saved: {}", feedback);
    }

    private void startCleanupTask() {
        scheduler.scheduleAtFixedRate(() -> {
            int beforeCleanup = feedbackSessions.size();

            feedbackSessions.entrySet().removeIf(entry -> {
                boolean shouldRemove = Duration.between(entry.getValue().getLastUpdated(), LocalDateTime.now())
                        .toMinutes() > TIMEOUT;
                if (shouldRemove) {
                    eventPublisher.publishEvent(new FeedbackSessionTimeoutEvent(entry.getValue().getUser()));
                    LOGGER.info("Удаление сессии обратной связи в связи с истечением таймаута в {} минут для пользователя: {}",
                            TIMEOUT,
                            entry.getKey());
                }
                return shouldRemove;
            });

            int afterCleanup = feedbackSessions.size();
            if (beforeCleanup != afterCleanup) {
                LOGGER.info("Очистка завершена: удалено {} сессий, осталось {}",
                        beforeCleanup - afterCleanup, afterCleanup);
            }
        }, TIMEOUT, TIMEOUT, TimeUnit.MINUTES);
    }

    public static Set<String> getBlackList() {
        return BLACK_LIST;
    }

}
