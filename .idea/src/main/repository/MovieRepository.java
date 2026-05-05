package ru.nurislam.springcourse.movieprojectmain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nurislam.springcourse.movieprojectmain.entities.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenreId(Long genreId);
    List<Movie> findByTitleContainingIgnoreCase(String keyword);
}

