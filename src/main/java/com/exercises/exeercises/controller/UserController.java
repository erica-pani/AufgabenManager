package com.exercises.exeercises.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.dto.UserDTO;
import com.exercises.exeercises.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<User> newUser(@RequestBody UserDTO userDTO) {

        User user = userService.saveNewUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
}
