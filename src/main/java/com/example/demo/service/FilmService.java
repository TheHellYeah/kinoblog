package com.example.demo.service;

import com.example.demo.model.Film;
import com.example.demo.model.Review;
import com.example.demo.model.User;

import java.util.List;

public interface FilmService {

    List<Film> getAll();
    void add(Film film);
    void addReview(Film film, Review review, String reviewerName);
}
