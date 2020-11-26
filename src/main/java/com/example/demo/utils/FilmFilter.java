package com.example.demo.utils;

import com.example.demo.model.Country;
import com.example.demo.model.Genre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmFilter {
    private Genre genre;
    private Country country;

    public boolean isEmpty() {
        return genre == null && country == null;
    }
}
