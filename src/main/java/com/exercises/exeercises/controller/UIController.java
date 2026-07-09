package com.exercises.exeercises.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



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

    @GetMapping("/board")
    public String board() {
        return "board";
    }
    
}
