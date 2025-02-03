package com.itc.maslenitsabot.feedback;

import com.itc.maslenitsabot.user.BotUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность обратной связи.
 *
 * @author smnadya
 * @since 2025.01.28
 */
@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BotUser user;

    private Integer eventRating;
    private Integer pancakesRating;
    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    private Integer step = 0;

    public Feedback() {
        this.lastUpdated = LocalDateTime.now();
    }

    /**
     * Класс текстовых констант опросника.
     */
    public static class Question {

        private static final String FQ_BASE = "\uD83D\uDCDD Обратная связь ";

        public static final String FQ_FIRST_MESSAGE = FQ_BASE + "[1/3]\n\n\uD83C\uDF1F Насколько вам понравилось мероприятие?";

        public static final String FQ_FIRST_CALLBACK_VALUE = "event_rating";

        public static final String FQ_SECOND_MESSAGE = FQ_BASE + "[2/3]\n\n\uD83E\uDD5E Как вы оцените вкус блинов?";

        public static final String FQ_SECOND_CALLBACK_VALUE = "pancakes_rating";

        public static final String FQ_THIRD_MESSAGE = FQ_BASE + "[3/3]\n\n\uD83C\uDF1F Что бы вы поменяли/добавили в процессе проведения Масленицы?";

        public static final String FQ_THANKS = "Спасибо за вашу обратную связь ❤";

        private Question() {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) &&
                Objects.equals(user, feedback.user) &&
                Objects.equals(eventRating, feedback.eventRating) &&
                Objects.equals(pancakesRating, feedback.pancakesRating) &&
                Objects.equals(comment, feedback.comment) &&
                Objects.equals(createdAt, feedback.createdAt) &&
                Objects.equals(step, feedback.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, eventRating, pancakesRating, comment, createdAt, step);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", eventRating=" + eventRating +
                ", pancakesRating=" + pancakesRating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                ", step=" + step +
                '}';
    }

}
