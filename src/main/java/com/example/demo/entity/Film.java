package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String preview; //путь к картинке
    private String trailer; //путь к видео на ют
    private short length;  //мин.
    private long budget;
    private float rating; //TODO

    @ElementCollection
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "film_id"))
    @Enumerated(value = EnumType.STRING)
    private List<Genre> genres;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}
