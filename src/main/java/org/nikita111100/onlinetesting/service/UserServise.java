package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServise {
    private final UserRepository userRepository;
    @Autowired
    public UserServise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByid(Integer id){
        return userRepository.getOne(id);
    }
    public List<User> findAll(){
    return userRepository.findAll();
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }
}

