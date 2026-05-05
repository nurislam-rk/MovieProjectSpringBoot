package ru.nurislam.springcourse.movieprojectmain.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nurislam.springcourse.movieprojectmain.entities.Movie;
import ru.nurislam.springcourse.movieprojectmain.entities.User;
import ru.nurislam.springcourse.movieprojectmain.service.FavoriteService;
import ru.nurislam.springcourse.movieprojectmain.service.MovieService;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final MovieService movieService;

    public FavoriteController(FavoriteService favoriteService,
                              MovieService movieService) {
        this.favoriteService = favoriteService;
        this.movieService = movieService;
    }

    @PostMapping("/add/{movieId}")
    public String add(@PathVariable Long movieId,
                      HttpSession session) {

        User user = (User) session.getAttribute("user");
        Movie movie = movieService.getMovieById(movieId);

        favoriteService.addToFavorite(user, movie);

        return "redirect:/movies";
    }

    @GetMapping("/favorite/{movieId}")
    public String addFavorite(@PathVariable Long movieId,
                              HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        Movie movie = movieService.getMovieById(movieId);
        favoriteService.addToFavorite(user, movie);

        return "redirect:/movies";
    }
}
