package org.nikita111100.onlinetesting.repository;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.Optional;

@Repository
public class QuestionDAO {
    @PersistenceContext
    EntityManager em;
    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(em.find(Question.class, id));
    }

    public long save(Question question) {
        Objects.requireNonNull( question);

        if ( question.getId() == null) {
            em.persist( question);
            return  question.getId();
        } else {
            return em.merge(question).getId();
        }
    }
    public void deleteQuestion(Question question){
        if ( question.getId() != null) {
            em.remove(question);
        }
    }
}
