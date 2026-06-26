package com.exercises.exeercises.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import com.exercises.exeercises.testconfig.IntegrationTestConfig;

@SpringBootTest
@Testcontainers
@Import(IntegrationTestConfig.class)
public class ExerciseIntegrationTests {
    

	@Autowired
	private PostgreSQLContainer<?> container;


	@Test 
    public void connection() {
        assertTrue(container.isCreated());
		assertTrue(container.isRunning());
    }
}
