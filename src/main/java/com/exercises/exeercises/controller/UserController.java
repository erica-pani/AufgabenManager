package com.exercises.exeercises.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.dto.UserDTO;
import com.exercises.exeercises.service.UserService;
import com.exercises.exeercises.model.UserPrincipal;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<User> newUser(@Valid @RequestBody UserDTO userDTO) {

        User user = userService.saveNewUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    } 

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        
        return new ResponseEntity<>(Map.of(
            "username", userDetails.getUsername(),
            "user_id", ((UserPrincipal) userDetails).getId()),
            HttpStatus.ACCEPTED );

    }
    

}
