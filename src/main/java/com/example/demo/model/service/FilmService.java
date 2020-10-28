package com.example.demo.model.service;

import com.example.demo.model.entities.Film;

import java.util.List;

public interface FilmService {

    List<Film> getAll();
    void add(Film film);
}
