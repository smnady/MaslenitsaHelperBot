package com.itc.maslenitsabot.feedback.event;

import com.itc.maslenitsabot.user.BotUser;

/**
 * Событие истечения времени сессии обратной связи.
 *
 * @author smnadya
 * @since 2025.02.02
 */
public record FeedbackSessionTimeoutEvent(BotUser user) {

}
