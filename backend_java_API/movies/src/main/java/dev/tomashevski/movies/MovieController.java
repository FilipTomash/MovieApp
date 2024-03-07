package dev.tomashevski.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;
    @GetMapping
    public ResponseEntity<Page<Movie>> getFilteredMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Movie> movies = movieService.getFilteredMovies(title, genre, genres, year, yearFrom, yearTo, page, size, sortBy);

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> getMovie(@PathVariable ObjectId id){
        return new ResponseEntity<Optional<Movie>>(movieService.getMovieById(id),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        return new ResponseEntity<Movie>(movieService.createMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getMovieReviews(@PathVariable ObjectId id) {
        try {
            List<Review> reviews = movieService.getMovieReviews(id);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Review> createReviewForMovie(@PathVariable String id, @RequestBody Map<String, String> payload) {
        String reviewBody = payload.get("reviewBody");
        Review createdReview = reviewService.createReview(reviewBody, id);

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<Movie> rateMovie(@PathVariable ObjectId id, @RequestBody Map<String, Integer> payload) {
        try {
            int rating = payload.get("rating");
            Movie ratedMovie = movieService.rateMovie(id, rating);

            return new ResponseEntity<>(ratedMovie, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
