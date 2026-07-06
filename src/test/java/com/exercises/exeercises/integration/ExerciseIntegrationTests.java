package com.exercises.exeercises.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import com.exercises.exeercises.config.IntegrationTestConfig;
import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.id.ExerciseId;
import com.exercises.exeercises.repository.BoardRepository;
import com.exercises.exeercises.repository.ExerciseRepository;

@SpringBootTest
@Testcontainers
@Import(IntegrationTestConfig.class)
public class ExerciseIntegrationTests {
    
	@Autowired
	private PostgreSQLContainer<?> container;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private BoardRepository boardRepository;

	private Long boardId;

	@Test 
    public void connection() {
        assertTrue(container.isCreated());
		assertTrue(container.isRunning());
    }

	@BeforeEach
	public void setUp() {
		Board board = boardRepository.findById(123L)
    		.orElseGet(() -> boardRepository.save(new Board()));


		Exercise exercise = new Exercise();
		exercise.setBoard(board);
		exercise.setId(new ExerciseId(board.getId(), 1));
		exercise.setTitle("Aufgabe1");
		exercise.setDescription("Ich bin Aufgabe 1");
		
		Exercise exercise2 = new Exercise();
		exercise2.setBoard(board);
		exercise2.setId(new ExerciseId(board.getId(), 2));
		exercise2.setTitle("Aufgabe2");
		exercise2.setDescription("Ich bin Aufgabe 2");
		

		Exercise exercise3 = new Exercise();
		exercise3.setBoard(board);
		exercise3.setId(new ExerciseId(board.getId(), 3));
		exercise3.setTitle("Aufgabe3");
		exercise3.setDescription("Ich bin Aufgabe 3");
		
		List<Exercise> exercises = List.of(
			exercise,
			exercise2,
			exercise3
		);

		exerciseRepository.saveAll(exercises);

		this.boardId = board.getId();
	}

	@Test
	public void findMaxExNumberTest() {

		Integer maxExNumber = exerciseRepository
			.findMaxExerciseNumber(boardId)
			.orElse(0);
		
		assertEquals(3, maxExNumber);

		maxExNumber = exerciseRepository
			.findMaxExerciseNumber(null)
			.orElse(0);

		assertEquals(0, maxExNumber);
	}
}
