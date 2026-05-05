package ru.nurislam.springcourse.movieprojectmain.service;

import org.springframework.stereotype.Service;
import ru.nurislam.springcourse.movieprojectmain.entities.Movie;
import ru.nurislam.springcourse.movieprojectmain.entities.Review;
import ru.nurislam.springcourse.movieprojectmain.entities.User;
import ru.nurislam.springcourse.movieprojectmain.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void addReview(User user, Movie movie, int rating, String comment) {

        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);
        review.setRating(rating);
        review.setComment(comment);

        reviewRepository.save(review);
    }

    public List<Review> getMovieReviews(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
