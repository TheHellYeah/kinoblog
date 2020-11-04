package com.example.demo.service;

import com.example.demo.entity.Film;
import com.example.demo.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Value("${film.default-preview}")
    private String defaultPreview;

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public void add(Film film) {
        if(film.getPreview() == null) film.setPreview(defaultPreview);
        filmRepository.save(film);
    }
}
