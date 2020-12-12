package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.repository.QuestionDAO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
public class QuestionService {

    private final QuestionDAO dao;

    public QuestionService(QuestionDAO dao) {
        this.dao = dao;
    }

    public Question findById(Long id) {
        return dao.findById(id)
                .orElse(null);
    }
    @Transactional
    public Long saveQuestion(Question question) {
        return dao.save(question);
    }
    @Transactional
    public void deleteQuestion(Question question) {
        dao.deleteQuestion(question);
    }
}
