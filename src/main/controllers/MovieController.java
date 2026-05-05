package ru.nurislam.springcourse.movieprojectmain.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nurislam.springcourse.movieprojectmain.entities.Movie;
import ru.nurislam.springcourse.movieprojectmain.entities.User;
import ru.nurislam.springcourse.movieprojectmain.repository.FavoriteRepository;
import ru.nurislam.springcourse.movieprojectmain.repository.GenreRepository;
import ru.nurislam.springcourse.movieprojectmain.repository.ReviewRepository;
import ru.nurislam.springcourse.movieprojectmain.service.FavoriteService;
import ru.nurislam.springcourse.movieprojectmain.service.MovieService;
import ru.nurislam.springcourse.movieprojectmain.service.ReviewService;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteService favoriteService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService,
                           GenreRepository genreRepository,
                           ReviewRepository reviewRepository,
                           FavoriteRepository favoriteRepository,
                           FavoriteService favoriteService,
                           ReviewService reviewService) {

        this.movieService = movieService;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
        this.favoriteRepository = favoriteRepository;
        this.favoriteService = favoriteService;
        this.reviewService = reviewService;
    }

    // 📌 HOME PAGE + FILTER BY GENRE
    @GetMapping
    public String getAll(@RequestParam(required = false) Long genreId,
                         @RequestParam(required = false) String keyword,
                         Model model) {

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("movies", movieService.search(keyword));
        } else if (genreId != null) {
            model.addAttribute("movies", movieService.getByGenre(genreId));
        } else {
            model.addAttribute("movies", movieService.getAllMovies());
        }

        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("selectedGenre", genreId);
        model.addAttribute("keyword", keyword);

        return "home";
    }

    // 📌 ADD MOVIE
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("genres", genreRepository.findAll());
        return "add-movie";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/movies";
    }

    // 📌 EDIT
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "edit-movie";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Movie movie) {
        movieService.updateMovie(id, movie);
        return "redirect:/movies";
    }

    // 📌 DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

    // ❤️ FAVORITE
    @PostMapping("/favorite/{movieId}")
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

    // 💬 REVIEW
    @PostMapping("/reviews/add/{movieId}")   // 🔥 FIX HERE
    public String addReview(@PathVariable Long movieId,
                            @RequestParam int rating,
                            @RequestParam String comment,
                            HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        Movie movie = movieService.getMovieById(movieId);
        reviewService.addReview(user, movie, rating, comment);

        return "redirect:/movies";
    }

}