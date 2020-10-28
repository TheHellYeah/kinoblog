package com.example.demo.web.controller;

import com.example.demo.model.entities.User;
import com.example.demo.model.service.FilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private FilmService filmService;

    public MainController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/")
    public ResponseEntity main() {
        return ResponseEntity.ok(new User("artem"));
    }
}
