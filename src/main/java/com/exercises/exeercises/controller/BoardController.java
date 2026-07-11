package com.exercises.exeercises.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.dto.BoardDTO;
import com.exercises.exeercises.model.dto.BoardResponseDTO;
import com.exercises.exeercises.service.BoardService;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/board")
public class BoardController {
    
    private final BoardService boardService;

    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/new")
    public ResponseEntity<Board> createNewBoard(@RequestBody BoardDTO boarddto) {
        
        Board board = boardService.createNewBoard(boarddto);
        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

    @GetMapping("/private/{userId}")
    public ResponseEntity<Collection<BoardResponseDTO>> privateBoards(@PathVariable Long userId) {
        
        Collection<BoardResponseDTO> boards = boardService.getPrivateBoards(userId);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
    
    
}
