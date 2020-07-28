package fr.tde.authentification.services;

import fr.tde.authentification.models.User;
import fr.tde.authentification.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User upsertUser(User user) {
        log.debug("upsert user in databases");
        return userRepository.save(user);
    }

    public User getUserById(Long id) throws NotFoundException {
        log.debug("Find the user with id : " + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if( optionalUser.isPresent() )
            return optionalUser.get();
        else
            throw new NotFoundException("User not found with id :" + id);
    }

    public User getUserByUsernameAndPassword(String username, String password) throws NotFoundException {
        log.debug("find the user with username : " + username);
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if( optionalUser.isPresent() ) {
            return optionalUser.get();
        }
        else {
            throw new NotFoundException("User not found with username :" + username);
        }
    }

    public User getUserByUsername(String username) throws NotFoundException {
        log.debug("find the user with username : " + username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new NotFoundException("USer not found with username : " + username);
        }
    }
}
