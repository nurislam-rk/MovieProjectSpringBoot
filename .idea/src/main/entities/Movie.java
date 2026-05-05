package ru.nurislam.springcourse.movieprojectmain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int year;
    private double rating;

    // ✅ НОВОЕ: ссылка на постер
    @Column(name = "image_url")
    private String imageUrl;

    // ✅ НОВОЕ: ссылка на страницу фильма
    @Column(name = "movie_url")
    private String movieUrl;

    // 🎭 ЖАНР
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    // 💬 ОТЗЫВЫ
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews;

    // ❤️ ИЗБРАННОЕ
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Favorite> favorites;
}