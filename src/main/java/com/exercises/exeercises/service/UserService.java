package com.exercises.exeercises.service;

import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.UserDTO;
import com.exercises.exeercises.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    public User saveNewUser(UserDTO user) {

        User userToBeSaved = new User();

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User mit diesem username existiert bereits");
        }
        
        userToBeSaved.setUsername(user.getUsername());
        userToBeSaved.setPassword(user.getPassword());

        return userRepository.save(userToBeSaved);
    }
}
