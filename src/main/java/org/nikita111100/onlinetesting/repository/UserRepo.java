package org.nikita111100.onlinetesting.repository;

import org.nikita111100.onlinetesting.model.persistent.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByName(String  name);
}
