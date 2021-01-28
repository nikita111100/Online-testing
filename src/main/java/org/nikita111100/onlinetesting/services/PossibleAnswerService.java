package org.nikita111100.onlinetesting.services;

import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repositories.PossibleAnswerRepo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PossibleAnswerService {

    //    private static final Logger logger = LoggerFactory.getLogger(PossibleAnswerService.class);
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
    public PossibleAnswer save(PossibleAnswer possibleAnswer) throws EntityNotFoundException {
        return pAnswerRepo.save(possibleAnswer);
    }

    @Transactional
    public void deleteById(Long id) throws EmptyResultDataAccessException {
        Optional<PossibleAnswer> possibleAnswer = pAnswerRepo.findById(id);
        if (possibleAnswer.isPresent()) {
            pAnswerRepo.deleteById(id);
        }
    }

    @Transactional
    public void createPossibleAnswerForm(String rolesChecked, PossibleAnswer possibleAnswer, Long questionId) {
        if (rolesChecked != null) {
            possibleAnswer.setCorrectAnswer(1);
        } else {
            possibleAnswer.setCorrectAnswer(0);
        }
        Optional<Question> question = questionService.findById(questionId);
        if (question.isPresent()) {
            possibleAnswer.setQuestions(question.get());
            pAnswerRepo.save(possibleAnswer);
        }
    }

    @Transactional
    public void updatePossibleAnswerForm(String rolesChecked,
                                         PossibleAnswer possibleAnswer) throws EntityNotFoundException {
        if (rolesChecked != null) {
            possibleAnswer.setCorrectAnswer(1);
        } else {
            possibleAnswer.setCorrectAnswer(0);
        }
        Optional<PossibleAnswer> possibleAnswerFromDb = pAnswerRepo.findById(possibleAnswer.getId());
        if (possibleAnswerFromDb.isPresent()) {
            pAnswerRepo.save(possibleAnswer);
        }
    }

}
