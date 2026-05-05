package ru.nurislam.springcourse.movieprojectmain.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nurislam.springcourse.movieprojectmain.entities.Movie;
import ru.nurislam.springcourse.movieprojectmain.entities.User;
import ru.nurislam.springcourse.movieprojectmain.service.MovieService;
import ru.nurislam.springcourse.movieprojectmain.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final MovieService movieService;

    public ReviewController(ReviewService reviewService,
                            MovieService movieService) {
        this.reviewService = reviewService;
        this.movieService = movieService;
    }

    @PostMapping("/add/{movieId}")
    public String add(@PathVariable Long movieId,
                      @RequestParam int rating,
                      @RequestParam String comment,
                      HttpSession session) {

        User user = (User) session.getAttribute("user");
        Movie movie = movieService.getMovieById(movieId);

        reviewService.addReview(user, movie, rating, comment);

        return "redirect:/movies";
    }
}