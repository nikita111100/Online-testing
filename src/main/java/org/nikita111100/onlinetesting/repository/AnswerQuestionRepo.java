package org.nikita111100.onlinetesting.repository;

import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerQuestionRepo extends JpaRepository<AnswerQuestion, Long> {
}
