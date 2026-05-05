package ru.nurislam.springcourse.movieprojectmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nurislam.springcourse.movieprojectmain.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}