package com.exercises.exeercises.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient.RequestBodySpec;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exercises.exeercises.config.IntegrationTestConfig;
import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.model.dto.ExerciseResponseDTO;
import com.exercises.exeercises.repository.BoardRepository;
import com.exercises.exeercises.repository.ExerciseRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import(IntegrationTestConfig.class)
@AutoConfigureRestTestClient
public class ExerciseControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
	private PostgreSQLContainer<?> container;

	@Autowired
	private ExerciseRepository exerciseRepository;

    @Autowired
	private BoardRepository boardRepository;

    private RestTestClient client;

    private Long boardId;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        client = RestTestClient.bindToApplicationContext(context).build();

        Board board = boardRepository.findById(123L)
    		.orElseGet(() -> boardRepository.save(new Board()));

        this.boardId = board.getId();
    }

    @AfterEach
    public void tearDown() {
        exerciseRepository.deleteAll();
        boardRepository.deleteAll();
    }
    
    @Test 
    public void connection() {
        assertTrue(container.isCreated());
		assertTrue(container.isRunning());
    }

    @Test
    public void shouldFindAllExercises() {
        ExerciseResponseDTO exerciseResponse = client.post().uri("/exercise/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ExerciseDTO("Titel", "Beschreibung", boardId))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();
        
        assertEquals("Titel", exerciseResponse.title());
        assertEquals("Beschreibung", exerciseResponse.description());
        assertEquals(boardId, exerciseResponse.boardId());
    }

}
