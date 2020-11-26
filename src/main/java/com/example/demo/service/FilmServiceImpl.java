package com.example.demo.service;

import com.example.demo.model.Film;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.FilmFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.example.demo.repository.FilmFilterSpecification.*;

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
    public List<Film> getAllByFilter(FilmFilter filter) {
        return filmRepository.findAll(byGenre(filter.getGenre())
                .and(byCountry(filter.getCountry())));
    }

    @Override
    public void addFilm(Film film) {
        if (film.getPreview() == null) {
            film.setPreview(defaultPreview);
        }
        filmRepository.save(film);
        log.info("Added new film: {}", film);
    }

    @Override
    public Review addReview(Film film, Review review, String reviewerName) {
        User reviewer = userRepository.findByUsername(reviewerName);
        if (isReviewExists(reviewer, film)) {
            log.info("Failed to add a review from user {} to film {}", reviewerName, film.getName());
            return null;
        }
        review.setDate(new Date());
        review.setFilm(film);
        review.setReviewer(reviewer);
        film.addReview(review);
        reviewer.addReview(review);
        reviewRepository.save(review);
        log.info("Added new review: {}", review);
        return review;
    }

    private boolean isReviewExists(User reviewer, Film film) {
        return reviewRepository.existsByReviewerAndFilm(reviewer, film);
    }
}
