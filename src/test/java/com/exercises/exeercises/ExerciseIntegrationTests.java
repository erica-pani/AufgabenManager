package com.exercises.exeercises;

import org.testcontainers.junit.jupiter.Container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ExerciseIntegrationTests {
    
    @Container
	static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:18.4");

	@DynamicPropertySource
	static void dynamicConfiguration(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
	}
}
