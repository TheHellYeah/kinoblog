package com.example.demo.model.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.Year;
import java.util.List;

@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private Year releaseYear;
    private String preview; //путь к картинке
    private String trailer; //путь к видео на ют
    private List<FilmGenre> genres;
    private List<Staff> staff;
    private short length;  //мин.
    private long budget;
    private float rating; //TODO
}
