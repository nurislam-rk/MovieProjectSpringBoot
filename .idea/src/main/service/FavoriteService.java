package ru.nurislam.springcourse.movieprojectmain.service;

import org.springframework.stereotype.Service;
import ru.nurislam.springcourse.movieprojectmain.entities.Favorite;
import ru.nurislam.springcourse.movieprojectmain.entities.Movie;
import ru.nurislam.springcourse.movieprojectmain.entities.User;
import ru.nurislam.springcourse.movieprojectmain.repository.FavoriteRepository;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void addToFavorite(User user, Movie movie) {

        if (favoriteRepository.existsByUserIdAndMovieId(user.getId(), movie.getId())) {
            return;
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovie(movie);

        favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long userId, Long movieId) {
        favoriteRepository.findByUserId(userId)
                .stream()
                .filter(f -> f.getMovie().getId().equals(movieId))
                .findFirst()
                .ifPresent(favoriteRepository::delete);
    }

    public List<Favorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}