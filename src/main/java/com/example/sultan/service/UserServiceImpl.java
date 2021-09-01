package com.example.sultan.service;

import com.example.sultan.exception.NotFoundException;
import com.example.sultan.model.Role;
import com.example.sultan.model.User;
import com.example.sultan.repository.RoleRepository;
import com.example.sultan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service @RequiredArgsConstructor @Slf4j @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        }else{
            log.info("User found in the database: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user to DB");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to DB",role.getRoleName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByroleName(roleName);
        user.getRoles().add(role);
        log.info("Adding role {} to user {}",roleName,username);
    }

    @Override
    public User getUseruser(String username){
        log.info("Fetching user {}",username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional getUser(String id) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isPresent()){
            User user = userOptional.get();
            String username = user.getUsername();
            log.info("Taking user {}",username);
            return userOptional;
        }else{
            log.info("Error");
            throw new NotFoundException("Error! User doesn't exist");
        }
    }

    @Override
    public List<User> retrieveAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Showing all users");
        return users;
    }

    @Override
    public User updateUser(String id, String username) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(username);
            userRepository.save(user);
            log.info("{} updated on id {} updated",username,id);
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
            userRepository.delete(userOptional.get());
            log.info("User deleted");
        }else{
            log.info("Error on deleting");
            throw new NotFoundException("Error");
        }
    }


}
