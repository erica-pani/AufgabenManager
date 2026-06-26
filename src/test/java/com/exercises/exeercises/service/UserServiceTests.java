package com.exercises.exeercises.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.dto.UserDTO;
import com.exercises.exeercises.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService target;

    private UserDTO user;
    String validUsername = "username";
    String validPassword = "password";

    @BeforeEach
    public void setUpTestCase() {
        user = new UserDTO(validUsername, validPassword);
    }

    @AfterEach
    public void tearDownTestCase() {
        user = null;
    }

    @Test
    public void saveNewUserTest() {

        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.existsByUsername(validUsername)).thenReturn(false);

        target.saveNewUser(user);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();

        assertEquals(validUsername, savedUser.getUsername());
        assertEquals(validPassword, savedUser.getPassword());
    }

    @Test
    public void saveNewUser_whenUsernameExists() {

        when(userRepository.existsByUsername(validUsername)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            target.saveNewUser(user);
        });

        verify(userRepository, never()).save(any());
    }
}
