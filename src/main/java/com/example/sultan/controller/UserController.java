package com.example.sultan.controller;

import com.example.sultan.exception.NotFoundException;
import com.example.sultan.model.User;

import com.example.sultan.repository.UserRepository;
import com.example.sultan.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>>retrieveAllUsers(){
        return ResponseEntity.ok().body(userService.retrieveAllUsers());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<Optional> getUser(@PathVariable String id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }
    @PostMapping("/adduser")
    public ResponseEntity<User>addNew(@RequestBody String name){
        return ResponseEntity.ok().body(userService.addNew(name));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User>updateUser(@PathVariable(value = "id") String id,@RequestBody String name){
        return ResponseEntity.ok().body(userService.updateUser(id,name));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void>deleteUser(@RequestBody String id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    }


