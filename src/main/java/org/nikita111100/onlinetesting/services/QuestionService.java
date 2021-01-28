package org.nikita111100.onlinetesting.services;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repositories.QuestionRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepository) {
        this.questionRepo = questionRepository;
    }

    public Optional<Question> findById(Long id) {
        return questionRepo.findById(id);
    }

    public  boolean isExists(Long id) {
        return questionRepo.existsById(id);
    }

    public  List<Question> findAll() {
        return questionRepo.findAll();
    }

    public  List<Question> findAllQuestionsByTestId(final Long test) {
        return questionRepo.findAllQuestionByTestId(test);
    }

    @Transactional
    public Question saveQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Transactional
    public void deleteById(Long id) {
        questionRepo.deleteById(id);
    }
}
