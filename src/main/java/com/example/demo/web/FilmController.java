package com.example.demo.web;

import com.example.demo.model.Film;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.FilmService;
import com.example.demo.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/{id:\\d+}")
    @JsonView(Views.FilmPage.class)
    public ResponseEntity filmPage(@PathVariable("id") Film film) {
        if(film != null) {
            return ResponseEntity.ok(film);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity getAddFilmPage() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addFilm(@RequestBody Film film) {
        filmService.addFilm(film);
    }

    @PostMapping("/{id:\\d+}/review")
    @JsonView(Views.FilmPage.class)
    public ResponseEntity addReview(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("id") Film film, @RequestBody Review review) {
        Review addedReview = filmService.addReview(film, review, principal.getUsername());
        if(addedReview != null) {
            return ResponseEntity.ok(addedReview);
        }
        return ResponseEntity.badRequest().body("Вы уже оставили отзыв к данному фильму.");
    }
}
