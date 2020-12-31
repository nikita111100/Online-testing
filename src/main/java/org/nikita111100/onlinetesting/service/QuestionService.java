package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repository.QuestionRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public boolean isExists(Long id){
        return questionRepo.existsById(id);
    }

    public List<Question> findAll(){
        return questionRepo.findAll();
    }

    public List<Question> findAllQuestionsByTestId(Long test){
        return questionRepo.findAllQuestionByTestId(test);
    }

    @Transactional
    public Question saveQuestion(Question question){
        return questionRepo.save(question);
    }

    @Transactional
    public void deleteById(Long id){
        questionRepo.deleteById(id);
    }
}
