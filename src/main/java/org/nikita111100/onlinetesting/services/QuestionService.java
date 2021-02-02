package org.nikita111100.onlinetesting.services;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.hibernate.StaleStateException;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.repositories.QuestionRepo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(PossibleAnswerService.class);

    private final QuestionRepo questionRepo;

    private final TestService testService;

    public QuestionService(QuestionRepo questionRepository, TestService testService) {
        this.questionRepo = questionRepository;
        this.testService = testService;
    }

    public Optional<Question> findById(Long id) {
        return questionRepo.findById(id);
    }

    public Question findOne(Long id) {
        return questionRepo.getOne(id);
    }

    public boolean isExists(Long id) {
        return questionRepo.existsById(id);
    }

    public List<Question> findAll() {
        return questionRepo.findAll();
    }

    public List<Question> findAllByTestId(final Long test) {
        return questionRepo.findAllQuestionByTestId(test);
    }

    @Transactional
    public Question saveQuestion(Question question) {
        try {
            return questionRepo.save(question);
        } catch (Exception e) {
            logger.error("Не удалось сохранить сущность");
            throw e;
        }
    }

    @Transactional
    public void deleteById(Long id) throws EmptyResultDataAccessException, StaleStateException {
        try {
            questionRepo.deleteById(id);
        } catch (Exception e) {
            logger.error("Не удалось удалить сущность");
            throw e;
        }
    }

    @Transactional
    public void create(Long testId, Question question) {
        try {
            Optional<Test> test = testService.findById(testId);
            question.setTest(test.get());
            questionRepo.save(question);
        } catch (Exception e) {
            logger.error("Не удалось сохранить сущность");
            throw e;
        }
    }

    @Transactional
    public void update(Question question) throws EntityNotFoundException {
        try {
            questionRepo.save(question);
        } catch (Exception e) {
            logger.error("Сущность для редактирования не найдена");
            throw e;
        }
    }
}
