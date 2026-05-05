package ru.nurislam.springcourse.movieprojectmain.service;

import org.springframework.stereotype.Service;
import ru.nurislam.springcourse.movieprojectmain.entities.Movie;
import ru.nurislam.springcourse.movieprojectmain.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 📌 ADD MOVIE
    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    // 📌 GET ALL
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // 📌 GET BY ID
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    // 📌 UPDATE
    public void updateMovie(Long id, Movie movie) {
        Movie existing = getMovieById(id);

        if (existing != null) {
            existing.setTitle(movie.getTitle());
            existing.setDescription(movie.getDescription());
            existing.setYear(movie.getYear());
            existing.setRating(movie.getRating());
            existing.setGenre(movie.getGenre());
            existing.setImageUrl(movie.getImageUrl());
            existing.setMovieUrl(movie.getMovieUrl());

            movieRepository.save(existing);
        }
    }

    // 📌 DELETE
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
    public List<Movie> getByGenre(Long genreId) {
        return movieRepository.findByGenreId(genreId);
    }
    public List<Movie> search(String keyword) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword);
    }
}