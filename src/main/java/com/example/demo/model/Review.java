package com.example.demo.model;

import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FilmPage.class)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "film_id")
    @JsonView(Views.UserPage.class)
    private Film film;

    @JsonView(Views.Public.class)
    private int mark;
    @JsonView(Views.Public.class)
    private String description;

    @Temporal(value = TemporalType.DATE)
    @JsonView(Views.Public.class)
    private Date date;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reviewer=" + reviewer.getUsername() +
                ", film=" + film.getName() +
                ", mark=" + mark +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
