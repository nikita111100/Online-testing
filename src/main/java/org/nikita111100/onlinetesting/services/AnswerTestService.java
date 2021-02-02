package org.nikita111100.onlinetesting.services;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.nikita111100.onlinetesting.repositories.AnswerTestRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerTestService {

    private static final Logger logger = LoggerFactory.getLogger(PossibleAnswerService.class);

    private final AnswerTestRepo answerTestRepo;

    public AnswerTestService(AnswerTestRepo answerTestRepository) {
        this.answerTestRepo = answerTestRepository;
    }

    public List<AnswerTest> findAll() {
        return answerTestRepo.findAll();
    }

    public List<AnswerTest> findAllByUserId(Long id) {
        return answerTestRepo.findAllByUserId(id);
    }

    public AnswerTest findAnswerTestById(Long id) {
        return answerTestRepo.getOne(id);
    }

    @Transactional
    public AnswerTest save(AnswerTest answerTest) {
        try {
            return answerTestRepo.save(answerTest);
        } catch (Exception e) {
            logger.error("Не удалось сохранить сущность");
            throw e;

        }
    }

    @Transactional
    public void deleteBy(Long id) {
        try {
            answerTestRepo.deleteById(id);
        } catch (Exception e) {
            logger.error("Не удалось удалить сущность");
            throw e;
        }
    }
}
