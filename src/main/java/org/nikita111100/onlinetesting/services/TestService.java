package org.nikita111100.onlinetesting.services;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.repositories.TestRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final TestRepo testRepo;

    public TestService(TestRepo testRepository) {
        this.testRepo = testRepository;
    }

    public Optional<Test> findById(Long id) {
        return testRepo.findById(id);
    }

    public List<Test> findAll() {
        return testRepo.findAll();
    }

    public boolean isExists(Long id) {
        return testRepo.existsById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        testRepo.deleteById(id);
    }

    @Transactional
    public Test saveTest(Test test) {
        return testRepo.save(test);
    }
}

