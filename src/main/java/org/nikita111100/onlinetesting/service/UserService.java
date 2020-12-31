package org.nikita111100.onlinetesting.service;

import org.nikita111100.onlinetesting.model.persistent.Role;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

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

    public boolean ifExists(Long id){
        return userRepo.existsById(id);
    }
    @Transactional
    public User saveUser(User user){
        user.setActive(true);
        return userRepo.save(user);
    }

    @Transactional
    public void deleteById(Long id){
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByName(s);
    }
}
