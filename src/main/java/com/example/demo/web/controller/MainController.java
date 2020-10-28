package com.example.demo.web.controller;

import com.example.demo.model.entities.Film;
import com.example.demo.model.service.FilmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    private FilmService filmService;

    public MainController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> index() {
        return filmService.getAll();
    }

    @PostMapping
    public Film addFilm(Film film) {
        filmService.add(film);
        return film;
    }
}
