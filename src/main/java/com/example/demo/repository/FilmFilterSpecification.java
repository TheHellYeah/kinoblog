package com.example.demo.repository;

import com.example.demo.model.Country;
import com.example.demo.model.Film;
import com.example.demo.model.Genre;
import org.springframework.data.jpa.domain.Specification;

public class FilmFilterSpecification {

    public static Specification<Film> byGenre(Genre genre) {
        return (Specification<Film>) (root, criteriaQuery, criteriaBuilder) -> {
            if(genre != null) {
                return criteriaBuilder.equal(root.get("genre"), genre);
            }
            return null;
        };
    }

    public static Specification<Film> byCountry(Country country) {
        return (Specification<Film>) (root, criteriaQuery, criteriaBuilder) -> {
            if(country != null) {
                return criteriaBuilder.equal(root.get("country"), country);
            }
            return null;
        };
    }
}
