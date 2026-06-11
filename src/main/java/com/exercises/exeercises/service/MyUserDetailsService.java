package com.exercises.exeercises.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.UserPrincipal;
import com.exercises.exeercises.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User mit dem username: " + username + " nicht gefunden"));

        return new UserPrincipal(user);

    }
    
}
