package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.repository.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final TestDao testDao;

    public TestService(TestDao testDao) {
        this.testDao = testDao;
    }

    public Test findById(Long id){
        return testDao.findById(id).orElse(null);
    }
}

