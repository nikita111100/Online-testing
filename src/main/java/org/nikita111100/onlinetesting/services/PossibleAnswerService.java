package org.nikita111100.onlinetesting.services;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repositories.PossibleAnswerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PossibleAnswerService {

    private static final Logger logger = LoggerFactory.getLogger(PossibleAnswerService.class);

    private final PossibleAnswerRepo pAnswerRepo;

    private final QuestionService questionService;

    public PossibleAnswerService(PossibleAnswerRepo pAnswerRepository, QuestionService questionService) {
        this.pAnswerRepo = pAnswerRepository;
        this.questionService = questionService;
    }

    public List<PossibleAnswer> findAll() {
        return pAnswerRepo.findAll();
    }

    public Optional<PossibleAnswer> findById(Long id) {
        return pAnswerRepo.findById(id);
    }

    public List<PossibleAnswer> findAllByQuestionId(Long id) {
        return pAnswerRepo.findAllByQuestionsId(id);
    }

    public List<PossibleAnswer> findAllByQuestion(Question question) {
        return pAnswerRepo.findAllByQuestions(question);
    }

    public boolean isExists(Long id) {
        return pAnswerRepo.existsById(id);
    }

    @Transactional
    public PossibleAnswer save(PossibleAnswer possibleAnswer) {
        try {
            return pAnswerRepo.save(possibleAnswer);
        } catch (Exception e) {
            logger.error("Не удалось сохранить сущность");
            throw e;

        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            pAnswerRepo.deleteById(id);
        } catch (Exception e) {
            logger.error("Не удалось удалить сущность");
            throw e;
        }
    }

    @Transactional
    public void create(String rolesChecked, PossibleAnswer possibleAnswer, Long questionId) {
        if (rolesChecked != null) {
            possibleAnswer.setCorrectAnswer(1);
        } else {
            possibleAnswer.setCorrectAnswer(0);
        }
        try {
            Optional<Question> question = questionService.findById(questionId);
            possibleAnswer.setQuestions(question.get());
            pAnswerRepo.save(possibleAnswer);
        } catch (Exception e) {
            logger.error("Сущность для сохранения не найдена");
            throw e;
        }
    }

    @Transactional
    public void update(String rolesChecked,
                       PossibleAnswer possibleAnswer) {
        if (rolesChecked != null) {
            possibleAnswer.setCorrectAnswer(1);
        } else {
            possibleAnswer.setCorrectAnswer(0);
        }
        try {
            pAnswerRepo.save(possibleAnswer);
        } catch (Exception e) {
            logger.error("Сущность для редактирования не найдена");
            throw e;
        }
    }

}
