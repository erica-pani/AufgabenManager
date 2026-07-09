package com.exercises.exeercises.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ExerciseRepository exerciseRepository;
    
    public BoardService(BoardRepository boardRepository, UserRepository userRepository, TeamRepository teamRepository, ExerciseRepository exerciseRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.exerciseRepository = exerciseRepository;
    }

    /**
     * Erstellt ein neues Board und weist es entweder einem Benutzer oder einem Team zu.
     * <p>
     * Die Eigentümerart wird anhand des Flags {@code ownerisUser} im {@link BoardDTO}
     * bestimmt. Anschließend wird geprüft, ob die angegebene Owner-ID existiert.
     * Das Board wird dem entsprechenden Benutzer bzw. Team hinzugefügt und danach
     * in der Datenbank gespeichert.
     * </p>
     *
     * @param boarddto DTO mit den Informationen für das anzulegende Board.
     *                 Enthält den Namen des Boards, die Owner-ID sowie die Information,
     *                 ob der Eigentümer ein Benutzer oder ein Team ist.
     * @return Das gespeicherte {@link Board}-Objekt inklusive der von der Datenbank
     *         vergebenen Werte.
     * @throws EntityNotFoundException wenn kein Benutzer bzw. Team mit der angegebenen
     *                                 Owner-ID gefunden wird.
     */
    public Board createNewBoard(BoardDTO boarddto) {

        Board boardToBeSaved = new Board();
        boardToBeSaved.setName(boarddto.name());
        boardToBeSaved.setOwner(Owner.USER);
        
        if (!boarddto.ownerisUser()) {
            boardToBeSaved.setOwner(Owner.TEAM);
        }

        if (boardToBeSaved.getOwner() == Owner.TEAM) {
            Team team = teamRepository
                .findById(boarddto.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("Team mit dieser id existiert nicht"));
            
            team.addBoard(boardToBeSaved);
            boardToBeSaved.setOwnerId(team.getId());
        }

        if (boardToBeSaved.getOwner() == Owner.USER) {
            User user = userRepository
                .findById(boarddto.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("User mit dieser id existiert nicht"));
            
            user.addBoard(boardToBeSaved);
            boardToBeSaved.setOwnerId(user.getId());
        }

        return boardRepository.save(boardToBeSaved);
    }

    public Board renameBoard(long boardId, String newName) {

        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new EntityNotFoundException("Board mit dieser Id wurde nicht gefunden"));
        
        board.setName(newName);

        return boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {

        if (!boardRepository.existsById(boardId)) {
            throw new EntityNotFoundException("Board mit dieser Id existiert nicht");
        }

        exerciseRepository.deleteAllByBoardId(boardId);

        boardRepository.deleteById(boardId);
    }

    public Collection<Board> getPrivateBoards(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User mit dieser Id wurde nicht gefunden");
        }

        Collection<Board> boards = boardRepository.findPrivateBoardsByOwnerId(userId);

        if (boards == null) {
            return List.of();
        }

        return boards;
    }

    public Collection<Board> getTeamBoards(Long teamId) {

        if (!teamRepository.existsById(teamId)) {
            throw new EntityNotFoundException("User mit dieser Id wurde nicht gefunden");
        }

        Collection<Board> boards = boardRepository.findTeamBoardsByOwnerId(teamId);

        if (boards == null) {
            return List.of();
        }

        return boards;
    }
}
