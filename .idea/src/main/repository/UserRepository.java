package ru.nurislam.springcourse.movieprojectmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nurislam.springcourse.movieprojectmain.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}