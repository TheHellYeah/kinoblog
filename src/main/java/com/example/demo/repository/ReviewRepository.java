package com.example.demo.repository;

import com.example.demo.model.Film;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByReviewerAndFilm(User user, Film film);
}
