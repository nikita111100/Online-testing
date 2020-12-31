package org.nikita111100.onlinetesting.repository;

import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PossibleAnswerRepo extends JpaRepository<PossibleAnswer,Long> {
    List<PossibleAnswer> findAllPossibleAnswerByQuestionsId(Long id);
}
