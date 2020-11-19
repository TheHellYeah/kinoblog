package com.example.demo.service;

import com.example.demo.model.Film;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${film.default-preview}")
    private String defaultPreview;

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public void add(Film film) {
        if(film.getPreview() == null) {
            film.setPreview(defaultPreview);
        }
        filmRepository.save(film);
        log.info("Added new film: {}", film);
    }

    @Override
    @Transactional
    public void addReview(Film film, Review review, String reviewerName) {
        if(isReviewExists(review)) {
            return;
        }
        User reviewer = userRepository.findByUsername(reviewerName);
        review.setDate(new Date());
        review.setFilm(film);
        review.setReviewer(reviewer);
        film.addReview(review);
        reviewer.addReview(review);
        reviewRepository.save(review);
        log.info("Added new review: {}", review);
    }

    private boolean isReviewExists(Review review) {
        return reviewRepository.existsByReviewerAndFilm(review.getReviewer(), review.getFilm());
    }
}
