package com.exercises.exeercises.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.Team;
import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.dto.BoardDTO;
import com.exercises.exeercises.model.enums.Owner;
import com.exercises.exeercises.repository.BoardRepository;
import com.exercises.exeercises.repository.ExerciseRepository;
import com.exercises.exeercises.repository.TeamRepository;
import com.exercises.exeercises.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTests {
    
    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private BoardService target;

    private User user;
    private Team team;
    Long teamId = 89023L;
    Long userId = 197834L;
    Long boardId = 71354L;
    String boardName = "neues Board";

    @BeforeEach
    public void setUpTestCase() {

        user = new User();
        user.setId(userId);

        team = new Team();
        team.setId(teamId);
    }

    @Test
    public void createNewBoard_whenOwnerIsUser() {

        BoardDTO boardDTO = new BoardDTO(boardName, true, userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(boardRepository.save(any(Board.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        target.createNewBoard(boardDTO);

        verify(teamRepository, never()).findById(anyLong());

        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);
        verify(boardRepository).save(captor.capture());
        Board savedBoard = captor.getValue();

        assertEquals(boardName, savedBoard.getName());
        assertEquals(Owner.USER, savedBoard.getOwner());
        assertEquals(userId, savedBoard.getOwnerId());
        assertEquals(0, savedBoard.getExercises().size());
        assertTrue(user.getBoards().contains(savedBoard));
    }

    @Test
    public void createNewBoard_whenOwnerIsTeam() {

        BoardDTO boardDTO = new BoardDTO(boardName, false, teamId);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(boardRepository.save(any(Board.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        target.createNewBoard(boardDTO);

        verify(userRepository, never()).findById(anyLong());

        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);
        verify(boardRepository).save(captor.capture());
        Board savedBoard = captor.getValue();

        assertEquals(boardName, savedBoard.getName());
        assertEquals(Owner.TEAM, savedBoard.getOwner());
        assertEquals(teamId, savedBoard.getOwnerId());
        assertEquals(0, savedBoard.getExercises().size());
        assertTrue(team.getBoards().contains(savedBoard));
    }

    @Test
    public void createNewBoard_whenUserNotFound() {

        BoardDTO boardDTO = new BoardDTO(boardName, true, userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            target.createNewBoard(boardDTO);
        });
        
        verify(boardRepository, never()).save(any());
    }

    @Test
    public void createNewBoard_whenTeamNotFound() {
    
        BoardDTO boardDTO = new BoardDTO(boardName, false, teamId);

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            target.createNewBoard(boardDTO);
        });
        
        verify(boardRepository, never()).save(any());
    }

    @Test
    public void renameBoardTest() {
        Board board = new Board();
        board.setId(boardId);
        board.setName(boardName);
        String newName = "neuer Name";

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(boardRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Board savedBoard = target.renameBoard(boardId, newName);

        assertEquals(newName, savedBoard.getName());
    }

    @Test
    public void renameBoard_whenBoardNotFound() {

        Board board = new Board();
        board.setId(boardId);
        board.setName(boardName);
        String newName = "neuer Name";

        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            target.renameBoard(boardId, newName);
        });
    }

    @Test
    public void deleteBoardTest() {

        when(boardRepository.existsById(boardId)).thenReturn(true);
        doNothing().when(exerciseRepository).deleteAllByIdBoardId(boardId);
        doNothing().when(boardRepository).deleteById(boardId);

        target.deleteBoard(boardId);
    }

    @Test
    public void deleteBoard_whenBoardNotFound() {

        when(boardRepository.existsById(boardId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            target.deleteBoard(boardId);
        });
    }
}
