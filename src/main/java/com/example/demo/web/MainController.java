package com.example.demo.web;

import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import com.example.demo.utils.FilmFilter;
import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    @JsonView(Views.Public.class)
    public List<Film> indexPage(FilmFilter filter) {
        if(!filter.isEmpty()) {
            return filmService.getAllByFilter(filter);
        }
        return filmService.getAll();
    }
}
