package com.example.sultan.service;

import com.example.sultan.model.Role;
import com.example.sultan.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    Optional getUser(String id);
    User getUseruser(String username);
    List<User> retrieveAllUsers();
    User updateUser(String id, String username);
    void deleteUser(String id);
}
