package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Traced
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> listAllUsers() {
        log.info(":: Listing All Users :: ");
        return userRepository.findAll().list();
    }

    public Optional<User> getUserById(Long id) {
        log.info(":: Fetching user with ID = " + id);
        return userRepository.findByIdOptional(id);
    }

    @Transactional
    public User createUser(User user) {
        log.info(":: Creating New User :: ");
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public Optional<User> updateUser(Long id, User updatedUser) {
        log.info(":: Updating User :: id = " + id + " User details :: " + updatedUser.toString());
        return userRepository.findByIdOptional(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return user;
        });
    }

    @Transactional
    public boolean deleteUser(Long id) {
        log.info(":: Deleting User with id = " + id);
        return userRepository.deleteById(id);
    }
}
