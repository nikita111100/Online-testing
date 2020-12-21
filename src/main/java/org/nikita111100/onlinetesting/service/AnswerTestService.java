package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.repository.AnswerTestRepo;
import org.springframework.stereotype.Service;

@Service
public class AnswerTestService {

    private final AnswerTestRepo answerTestRepo;

    public AnswerTestService(AnswerTestRepo answerTestRepo) {
        this.answerTestRepo = answerTestRepo;
    }
}
