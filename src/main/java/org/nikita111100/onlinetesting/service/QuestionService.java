package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repository.QuestionRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    public QuestionService( QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }
    public Question findById(Long id){
        return questionRepo.getOne(id);
    }
    public List<Question> findAll(){
        return questionRepo.findAll();
    }
    public Question saveQuestion(Question question){
        return questionRepo.save(question);
    }
    public void deleteById(Long id){
        questionRepo.deleteById(id);
    }
}
