package org.nikita111100.onlinetesting.services;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(PossibleAnswerService.class);

    private final UserRepo userRepo;

    public UserService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public User findByName(String user) {
        try {
            return userRepo.findByName(user);
        } catch (Exception e) {
            logger.error("Не удалось найти сущность");
            throw e;
        }
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public boolean ifExists(Long id) {
        return userRepo.existsById(id);
    }

    @Transactional
    public User saveUser(User user) {
        try {
            user.setActive(true);
            return userRepo.save(user);
        } catch (Exception e) {
            logger.error("Не удалось cохранить сущность");
            throw e;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            logger.error("Не удалось удалить сущность");
            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        try {
            return userRepo.findByName(name);
        } catch (Exception e) {
            logger.error("не удалось найти пользователя");
            throw e;
        }
    }
}
