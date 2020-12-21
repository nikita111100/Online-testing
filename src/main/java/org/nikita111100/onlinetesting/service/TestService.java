package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.repository.TestRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestRepo testRepo;


    public TestService(TestRepo testRepo) {
        this.testRepo = testRepo;
    }

    public Test findById (Long id){
        return testRepo.getOne(id);
    }

    public List<Test> findAll (){
        return testRepo.findAll();
    }
}

