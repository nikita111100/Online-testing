package org.nikita111100.onlinetesting.repository;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class TestDao {
    @PersistenceContext
    EntityManager em;

    public Optional<Test> findById(Long id){
        return Optional.ofNullable(em.find(Test.class, id));

    }

}
