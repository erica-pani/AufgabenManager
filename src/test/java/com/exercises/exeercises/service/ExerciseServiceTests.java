package com.exercises.exeercises.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.model.enums.Status;
import com.exercises.exeercises.model.id.ExerciseId;
import com.exercises.exeercises.repository.BoardRepository;
import com.exercises.exeercises.repository.ExerciseRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTests {
    
    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private ExerciseService target;

    private Board board;
    private ExerciseDTO exerciseDTO;
    private ExerciseId exerciseId;
    private Exercise exercise;

    Long boardId = 17L;
    String title = "Titel";
    String description = "Beschreibung";

    @BeforeEach
    public void setUpTestCase() {
        board = new Board();
        board.setId(boardId);

        exerciseDTO = new ExerciseDTO(title, description, boardId);

        exerciseId = new ExerciseId();
        exercise = new Exercise();
    }

    @Test
    public void saveNewExerciseTest() {

        when(exerciseRepository.findMaxExerciseNumber(boardId)).thenReturn(Optional.empty());
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(exerciseRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        target.saveNewExercise(exerciseDTO);

        assertEquals(1, board.getExercises().size());

        ArgumentCaptor<Exercise> captor = ArgumentCaptor.forClass(Exercise.class);
        verify(exerciseRepository).save(captor.capture());
        Exercise savedExercise = captor.getValue();

        assertEquals(board, savedExercise.getBoard());
        assertEquals(LocalDate.now(), savedExercise.getCreationDate());
        assertEquals(description, savedExercise.getDescription());
        assertEquals(Status.TODO, savedExercise.getStatus());
        assertEquals(title, savedExercise.getTitle());
        assertEquals(boardId, savedExercise.getId().getBoardId());
        assertEquals(1, savedExercise.getId().getExerciseNumber());
    }

    @Test
    public void saveNewExercise_whenBoardDoesNotExist() {

        when(exerciseRepository.findMaxExerciseNumber(boardId)).thenReturn(Optional.empty());
        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            target.saveNewExercise(exerciseDTO);
        });

        verify(exerciseRepository, never()).save(any());
    }

    @Test
    public void setExerciseToDoneTest() {

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        target.setExerciseToDone(exerciseId);

        assertEquals(Status.DONE, exercise.getStatus());

        verify(exerciseRepository).save(exercise);

    }


    @Test
    public void setExerciseToDone_whenExerciseNotFound() {

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            target.setExerciseToDone(exerciseId);
        });

        verify(exerciseRepository, never()).save(any());
    }

    @Test
    public void setExerciseToInProgressTest() {

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        target.setExerciseToInProgress(exerciseId);

        assertEquals(Status.IN_PROGRESS, exercise.getStatus());

        verify(exerciseRepository).save(exercise);

    }


    @Test
    public void setExerciseToInProgress_whenExerciseNotFound() {

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            target.setExerciseToInProgress(exerciseId);
        });

        verify(exerciseRepository, never()).save(any());
    }

    @Test
    public void deleteExerciseTest() {

        doNothing().when(exerciseRepository).deleteById(any());
        when(exerciseRepository.existsById(exerciseId)).thenReturn(true);

        target.deleteExercise(exerciseId);

        verify(exerciseRepository).deleteById(exerciseId);
    }

    @Test
    public void deleteExercise_whenExerciseNotFound() {

        when(exerciseRepository.existsById(exerciseId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            target.deleteExercise(exerciseId);
        });

        verify(exerciseRepository, never()).deleteById(any());
    }

    @Test
    public void editExerciseTest() {

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        target.editExercise(exerciseDTO, exerciseId);

        assertEquals(title, exercise.getTitle());
        assertEquals(description, exercise.getDescription());
        
        verify(exerciseRepository).save(exercise);
    }

    @Test
    public void editExercise_whenExerciseNotFound() {

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            target.editExercise(exerciseDTO, exerciseId);
        });

        verify(exerciseRepository, never()).save(any());
    }

    @Test
    public void getExerciseByBoardIdTest() {

        Long boardId = 1242L;

        when(exerciseRepository.findAllByIdBoardId(boardId)).thenReturn(List.of());

        Collection<Exercise> list = target.getExercisesByBoardId(boardId);
    }

}
