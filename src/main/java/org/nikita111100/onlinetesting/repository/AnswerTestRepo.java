package org.nikita111100.onlinetesting.repository;

import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerTestRepo extends JpaRepository<AnswerTest, Long> {

}
