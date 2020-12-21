package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.repository.AnswerQuestionRepo;
import org.springframework.stereotype.Service;

@Service
public class AnswerQuestionService {

    private final AnswerQuestionRepo answerQuestionRepo;

    public AnswerQuestionService(AnswerQuestionRepo answerQuestionRepo) {
        this.answerQuestionRepo = answerQuestionRepo;
    }
}
