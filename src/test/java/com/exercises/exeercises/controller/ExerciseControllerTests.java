package com.exercises.exeercises.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exercises.exeercises.config.IntegrationTestConfig;
import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.model.dto.ExerciseResponseDTO;
import com.exercises.exeercises.model.id.ExerciseId;
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
    void saveNewExercise() {
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
        assertEquals(1, exerciseResponse.exerciseNumber());
    }

    @Test
    void setExerciseToDone() {
        ExerciseResponseDTO exerciseResponse = client.post().uri("/exercise/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ExerciseDTO("Titel", "Beschreibung", boardId))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();
        
        assertEquals("TODO", exerciseResponse.status());
        
        exerciseResponse = client.put()
            .uri("/exercise/done/" + exerciseResponse.boardId() + "/" + exerciseResponse.exerciseNumber())
            .exchange()
            .expectStatus().isOk()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();

        assertEquals("DONE", exerciseResponse.status());
    }

    @Test
    void setExerciseToInProgress() {
        ExerciseResponseDTO exerciseResponse = client.post().uri("/exercise/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ExerciseDTO("Titel", "Beschreibung", boardId))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();
        
        assertEquals("TODO", exerciseResponse.status());
        
        exerciseResponse = client.put()
            .uri("/exercise/inProgress/" + exerciseResponse.boardId() + "/" + exerciseResponse.exerciseNumber())
            .exchange()
            .expectStatus().isOk()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();

        assertEquals("IN_PROGRESS", exerciseResponse.status());
    }

    @Test
    void editExercise() {
        ExerciseResponseDTO exerciseResponse = client.post().uri("/exercise/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ExerciseDTO("Titel", "Beschreibung", boardId))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();

        exerciseResponse = client.put()
            .uri(String.format("/exercise/edit?boardId=%s&exerciseNumber=%s", exerciseResponse.boardId(), exerciseResponse.exerciseNumber()))
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ExerciseDTO("Anderer Titel", "Andere Beschreibung", boardId))
            .exchange()
            .expectStatus().isAccepted()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();

        assertEquals("Anderer Titel", exerciseResponse.title());
        assertEquals("Andere Beschreibung", exerciseResponse.description());
    }

    @Test
    void deleteExercise() {
        ExerciseResponseDTO exerciseResponse = client.post().uri("/exercise/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ExerciseDTO("Titel", "Beschreibung", boardId))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ExerciseResponseDTO.class)
            .returnResult()
            .getResponseBody();

        String response = client.delete().uri(String.format("/exercise/delete/%s/%s", exerciseResponse.boardId(), exerciseResponse.exerciseNumber()))
        .exchange()
        .expectStatus().isAccepted()
        .expectBody(String.class)
        .returnResult()
        .getResponseBody();

        assertFalse(exerciseRepository.existsById(new ExerciseId(exerciseResponse.boardId(), exerciseResponse.exerciseNumber())));
        assertEquals("Aufgabe wurde geloescht", response);
    }

}
