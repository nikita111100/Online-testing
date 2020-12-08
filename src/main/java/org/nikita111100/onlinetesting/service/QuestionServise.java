package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class QuestionServise {
    private final EntityManager em;

    private final QuestionRepository questionRepository;

    public QuestionServise(QuestionRepository questionRepository, EntityManager em) {
        this.questionRepository = questionRepository;
        this.em = em;
    }

    public Question findByid(Integer id){
        return questionRepository.getOne(id);
    }

    public List<Question> findAll(){
        return questionRepository.findAll();
    }

    public Question saveQuestion(Question question){
        return questionRepository.save(question);
    }

    public void deleteById(Integer id){
            questionRepository.deleteById(id);
    }

    public List<Question> findAllQuestionByTest(Integer testId){
        Query query = em.createNamedQuery("getAllQuestionsByTest");
        query.setParameter(1,testId);
        List<Question> a = query.getResultList();
        return a;
    }


}
