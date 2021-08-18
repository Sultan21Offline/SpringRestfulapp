package com.example.sultan.service;

import com.example.sultan.model.User;

import java.util.Optional;

public interface UserService {

    User addNew(String name);

    Optional getUser(String id);

    Iterable<User> retrieveAllUsers();

    User updateUser(String id, String name);

    void deleteUser(String id);
}
