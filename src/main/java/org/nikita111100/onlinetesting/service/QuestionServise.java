package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServise {
    private final QuestionRepository questionRepository;
    @Autowired
    public QuestionServise(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
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
}
