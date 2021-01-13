package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.nikita111100.onlinetesting.repository.AnswerTestRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerTestService {

    private final AnswerTestRepo answerTestRepo;

    public AnswerTestService(AnswerTestRepo answerTestRepository) {
        this.answerTestRepo = answerTestRepository;
    }

    public List<AnswerTest> findAll() {
        return answerTestRepo.findAll();
    }

    @Transactional
    public void deleteBy(Long id) {
        answerTestRepo.deleteById(id);
    }
}
