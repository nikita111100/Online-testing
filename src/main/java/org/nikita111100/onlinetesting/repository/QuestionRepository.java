package org.nikita111100.onlinetesting.repository;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository <Question,Integer>{
    List<Question> findAllQuestionByTestId(Integer testID);
}
