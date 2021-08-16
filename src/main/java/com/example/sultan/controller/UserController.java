package com.example.sultan.controller;

import com.example.sultan.exception.NotFoundException;
import com.example.sultan.model.User;

import com.example.sultan.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> retrieveAllUsers(){
        Iterable<User> userDB = userRepository.findAll();
        return userDB;
    }
    @GetMapping("{id}")
    public Optional getUser(@PathVariable String id){
        Optional<User> userDB = userRepository.findById(UUID.fromString(id));
        if(userDB.isPresent()){
            return userDB;
        }else{
            throw new NotFoundException("Error! User doesn't exist");

        }
    }
    @PostMapping("create")
    public User addNew(@RequestBody String name){
        User user = new User(name);
        userRepository.save(user);
        return user;
    }

    @PutMapping("update/{id}")
    public User updateUser(@PathVariable(value = "id") String id,@RequestBody String name){
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(name);
            userRepository.save(user);
            return user;
        }else{
            throw new NotFoundException("Error not found user");
        }
    }

    @DeleteMapping("delete")
    public void deleteUser(@RequestBody String id){
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isPresent()){
            userRepository.delete(userOptional.get());
        }else{
            throw new NotFoundException("Error");
        }
    }

}
