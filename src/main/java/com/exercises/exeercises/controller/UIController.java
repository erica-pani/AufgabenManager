package com.exercises.exeercises.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UIController {
    

    @GetMapping("/")
    public String home() {
        return "main";
    }
    
}
