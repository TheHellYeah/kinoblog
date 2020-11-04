package com.example.demo.service;

import com.example.demo.entity.Film;

import java.util.List;

public interface FilmService {

    List<Film> getAll();
    void add(Film film);
}
