package com.example.demo.web;

import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addFilm(@RequestBody Film film) {
        filmService.add(film);
    }
}
