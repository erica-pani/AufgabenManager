package com.exercises.exeercises.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.dto.UserDTO;
import com.exercises.exeercises.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    
    public User saveNewUser(UserDTO user) {

        User userToBeSaved = new User();

        if (userRepository.existsByUsername(user.username())) {
            throw new IllegalArgumentException("User mit diesem username existiert bereits");
        }
        
        userToBeSaved.setUsername(user.username());
        userToBeSaved.setPassword(encoder.encode(user.password()));

        return userRepository.save(userToBeSaved);
    }
}
