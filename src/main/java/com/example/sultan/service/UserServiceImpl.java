package com.example.sultan.service;

import com.example.sultan.exception.NotFoundException;
import com.example.sultan.model.User;
import com.example.sultan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User addNew(String name) {
        User user = new User(name);
        log.info("Adding new User",user.getName());
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional getUser(String id) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isPresent()){
            User user = userOptional.get();
            String Username = user.getName();
            log.info("Taking user {}",Username);
            return userOptional;
        }else{
            log.info("Error");
            throw new NotFoundException("Error! User doesn't exist");

        }
    }

    @Override
    public Iterable<User> retrieveAllUsers() {
        log.info("Showing all users");
        Iterable<User> userDB = userRepository.findAll();
        return userDB;
    }

    @Override
    public User updateUser(String id, String name) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(name);
            userRepository.save(user);
            log.info("{} updated on id {} updated",name,id);
            return user;
        }else{
            log.info("Error on updating");
            throw new NotFoundException("Error not found user");
        }
    }


    @Override
    public void deleteUser(String id) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isPresent()){
            log.info("User deleted");
            userRepository.delete(userOptional.get());
        }else{
            log.info("Error on deleting");
            throw new NotFoundException("Error");
        }
    }
}
