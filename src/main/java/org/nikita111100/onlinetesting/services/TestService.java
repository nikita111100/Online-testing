package org.nikita111100.onlinetesting.services;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.repositories.TestRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(PossibleAnswerService.class);

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
        try {
            testRepo.deleteById(id);
        } catch (Exception e) {
            logger.error("Не удалось удалить сущность");
            throw e;
        }
    }

    @Transactional
    public Test saveTest(Test test) {
        try {
            return testRepo.save(test);
        } catch (Exception e) {
            logger.error("Не удалось сохранить сущность");
            throw e;

        }
    }
}

