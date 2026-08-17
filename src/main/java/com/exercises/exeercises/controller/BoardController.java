package com.exercises.exeercises.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.dto.BoardDTO;
import com.exercises.exeercises.model.dto.BoardResponseDTO;
import com.exercises.exeercises.service.BoardService;

import jakarta.validation.Valid;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/board")
public class BoardController {
    
    private final BoardService boardService;

    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/new")
    public ResponseEntity<BoardResponseDTO> createNewBoard(@Valid @RequestBody BoardDTO boarddto) {
        
        BoardResponseDTO board = boardService.createNewBoard(boarddto);
        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

    @GetMapping("/private/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Collection<BoardResponseDTO>> privateBoards(@PathVariable Long userId) {
        
        Collection<BoardResponseDTO> boards = boardService.getPrivateBoards(userId);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<Collection<BoardResponseDTO>> TeamBoards(@PathVariable Long teamId) {
        
        Collection<BoardResponseDTO> boards = boardService.getTeamBoards(teamId);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @PutMapping("/rename/{boardId}")
    public ResponseEntity<BoardResponseDTO> renameBoard(@PathVariable Long boardId, @RequestParam String name) {
    
        BoardResponseDTO board = boardService.renameBoard(boardId, name);
        return new ResponseEntity<>(board, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) {

        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    
    
    
    
}
