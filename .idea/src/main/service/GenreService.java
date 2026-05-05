package ru.nurislam.springcourse.movieprojectmain.service;

import org.springframework.stereotype.Service;
import ru.nurislam.springcourse.movieprojectmain.entities.Genre;
import ru.nurislam.springcourse.movieprojectmain.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
