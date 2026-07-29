package com.exercises.exeercises.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class UIController {
    
    @GetMapping("/")
    public String home() {
        return "main";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/{boardName}")
    public String board(@RequestParam(name = "bN", required = true) String boardName, @RequestParam(name = "bid", required = true) Long boardId, Model model) {
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", boardId);
        return "board";
    }

}
