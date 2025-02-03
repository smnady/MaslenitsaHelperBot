package com.itc.maslenitsabot.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * DAO обратной связи.
 *
 * @author smnadya
 * @since 2025.01.28
 */
@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, Long> {

}
