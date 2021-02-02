package org.nikita111100.onlinetesting.services;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.nikita111100.onlinetesting.repositories.AnswerQuestionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerQuestionService {

    private static final Logger logger = LoggerFactory.getLogger(PossibleAnswerService.class);

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
        try {
            return answerQuestionRepo.save(answerQuestion);
        } catch (Exception e) {
            logger.error("Не удалось сохранить сущность");
            throw e;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            answerQuestionRepo.deleteById(id);
        } catch (Exception e) {
            logger.error("Не удалось удалить сущность");
            throw e;
        }
    }

    public boolean isExists(Long id) {
        return answerQuestionRepo.existsById(id);
    }
}
