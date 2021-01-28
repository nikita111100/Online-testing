package org.nikita111100.onlinetesting.repositories;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {
}
