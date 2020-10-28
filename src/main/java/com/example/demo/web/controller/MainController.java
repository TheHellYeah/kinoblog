package com.example.demo.web.controller;

import com.example.demo.model.entities.Film;
import com.example.demo.model.service.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin("http://localhost:4200")
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
