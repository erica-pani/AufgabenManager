package com.exercises.exeercises.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.dto.BoardDTO;
import com.exercises.exeercises.service.BoardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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
    
}
