package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Role;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findById(Long id){
        return userRepo.getOne(id);
    }

    public User findByName(String user){
        return userRepo.findByName(user);
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    @Transactional
    public User saveUser(User user){
        return userRepo.save(user);
    }
    @Transactional
    public void deleteById(Long id){
        userRepo.deleteById(id);
    }
}
