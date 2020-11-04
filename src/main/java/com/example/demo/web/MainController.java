package com.example.demo.web;

import com.example.demo.entity.Film;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<Film> index() {
        return filmService.getAll();
    }

    @PostMapping("/add")
    public void addFilm(@RequestBody Film film) {
        filmService.add(film);
    }
}
