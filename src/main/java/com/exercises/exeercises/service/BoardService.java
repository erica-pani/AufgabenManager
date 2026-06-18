package com.exercises.exeercises.service;

import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.Team;
import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.dto.BoardDTO;
import com.exercises.exeercises.model.enums.Owner;
import com.exercises.exeercises.repository.BoardRepository;
import com.exercises.exeercises.repository.TeamRepository;
import com.exercises.exeercises.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    
    public BoardService(BoardRepository boardRepository, UserRepository userRepository, TeamRepository teamRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    public Board createNewBoard(BoardDTO boarddto) {

        Board boardToBeSaved = new Board();
        boardToBeSaved.setName(boarddto.getName());
        boardToBeSaved.setOwner(boarddto.getOwner());

        if (boardToBeSaved.getOwner() == Owner.TEAM) {
            Team team = teamRepository
                .findById(boarddto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("Team mit dieser id existiert nicht"));
            
            team.addBoard(boardToBeSaved);
            boardToBeSaved.setOwnerId(team.getId());
        }

        if (boardToBeSaved.getOwner() == Owner.USER) {
            User user = userRepository
                .findById(boarddto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("User mit dieser id existiert nicht"));
            
            user.addBoard(boardToBeSaved);
            boardToBeSaved.setOwnerId(user.getId());
        }

        return boardRepository.save(boardToBeSaved);
    }
}
