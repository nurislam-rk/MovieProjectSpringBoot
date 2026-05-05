package ru.nurislam.springcourse.movieprojectmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nurislam.springcourse.movieprojectmain.entities.Favorite;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

    List<Favorite> findByUserId(Long userId);
}
