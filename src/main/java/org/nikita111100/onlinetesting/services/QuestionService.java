package org.nikita111100.onlinetesting.services;

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
        return questionRepo.save(question);
    }

    @Transactional
    public void deleteById(Long id) throws EmptyResultDataAccessException, StaleStateException {
        Optional<Question> question = questionRepo.findById(id);
        if (question.isPresent()) {
            questionRepo.deleteById(id);
        }
    }

    @Transactional
    public void createQuestionsForm(Long testId, Question question) {
        Optional<Test> test = testService.findById(testId);
        if (test.isPresent()) {
            question.setTest(test.get());
            questionRepo.save(question);
        }
    }

    @Transactional
    public void updateQuestionsForm(Question question) throws EntityNotFoundException {
        Optional<Question> questionFromDb = questionRepo.findById(question.getId());
        if (questionFromDb.isPresent()) {
            questionRepo.save(question);
        }
    }
}
