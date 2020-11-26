package com.example.demo.service;

import com.example.demo.model.Film;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.utils.FilmFilter;

import java.util.List;

public interface FilmService {

    List<Film> getAll();
    List<Film> getAllByFilter(FilmFilter filter);
    void addFilm(Film film);
    Review addReview(Film film, Review review, String reviewerName);
}
