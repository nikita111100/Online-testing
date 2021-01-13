package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.nikita111100.onlinetesting.repository.AnswerQuestionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerQuestionService {

    private final AnswerQuestionRepo answerQuestionRepo;

    public AnswerQuestionService(AnswerQuestionRepo answerQuestionRepository) {
        this.answerQuestionRepo = answerQuestionRepository;
    }

    public List<AnswerQuestion> findAll() {
        return answerQuestionRepo.findAll();
    }

    public AnswerQuestion findById(Long id) {
        return answerQuestionRepo.getOne(id);
    }

    @Transactional
    public AnswerQuestion save(AnswerQuestion answerQuestion) {
        return answerQuestionRepo.save(answerQuestion);
    }

    @Transactional
    public void deleteById(Long id) {
        answerQuestionRepo.deleteById(id);

    }

    public boolean isExists(Long id) {
        return answerQuestionRepo.existsById(id);
    }
}
