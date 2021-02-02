package org.nikita111100.onlinetesting.repositories;

import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerTestRepo extends JpaRepository<AnswerTest, Long> {

    List<AnswerTest> findAllByUserId(Long id);
}
