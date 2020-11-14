package com.example.demo.web;

import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/{id:\\d+}")
    public ResponseEntity filmPage(@PathVariable("id") Film film) {
        if(film != null) {
            return ResponseEntity.ok(film);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addFilm(@RequestBody Film film) {
        filmService.add(film);
    }
}
