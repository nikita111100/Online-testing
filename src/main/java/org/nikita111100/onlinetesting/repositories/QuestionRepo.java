package org.nikita111100.onlinetesting.repositories;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findAllQuestionByTestId(Long test);
}
