package com.example.demo.model;

import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@Entity
@Data
@XmlRootElement(name = "film")
@XmlAccessorType(XmlAccessType.FIELD)
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Public.class)
    @XmlAttribute
    private long id;

    @JsonView(Views.Public.class)
    private String name;

    @Column(length = 1000)
    @JsonView(Views.Public.class)
    private String description;

    @JsonView(Views.Public.class)
    private String preview; //путь к картинке

    @JsonView(Views.Public.class)
    private String trailer; //путь к видео на ют

    @JsonView(Views.Public.class)
    private short length;  //мин.

    @JsonView(Views.Public.class)
    private short releaseYear;

    @JsonView(Views.Public.class)
    private float rating;

    @JsonView(Views.Public.class)
    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @JsonView(Views.Public.class)
    @Enumerated(value = EnumType.STRING)
    private Country country;

    @JsonView(Views.FilmPage.class)
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @XmlTransient
    private List<Review> reviews;

    public void addReview(Review review) {
        reviews.add(review);
        recountRating();
    }

    private void recountRating() {
        float sum = reviews.stream().map(Review::getMark).reduce(0, Integer::sum);
        this.rating = sum / reviews.size();
    }
}
