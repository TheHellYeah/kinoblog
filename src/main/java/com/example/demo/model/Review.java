package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    @JsonIgnore
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "film_id")
    @JsonIgnore
    private Film film;

    private int mark;
    private String description;

    @Temporal(value = TemporalType.DATE)
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
