package com.exercises.exeercises.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;

import com.exercises.exeercises.repository.ExerciseRepository;

public class ExerciseControllerTests {

    @Autowired
	private PostgreSQLContainer<?> container;

	@Autowired
	private ExerciseRepository exerciseRepository;

    @BeforeEach
    public void setUp() {
        
    }

}
