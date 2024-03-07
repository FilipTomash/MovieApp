package dev.tomashevski.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Page<Movie> getFilteredMovies(String title, String genre, List<String> genres, Integer year, Integer yearFrom, Integer yearTo, int page, int size, String sortBy) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Movie exampleMovie = new Movie();
        exampleMovie.setTitle(title);
        exampleMovie.setGenres(genres);

        Example<Movie> example = Example.of(exampleMovie, matcher);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));

        if (year != null) {
            return movieRepository.findByReleaseYear(year, pageRequest);
        } else if (yearFrom != null && yearTo != null) {
            return movieRepository.findByReleaseYearBetween(yearFrom, yearTo, pageRequest);
        } else if (title != null) {
            return movieRepository.findByTitle(title, pageRequest);
        } else if (genre != null) {
            return movieRepository.findByGenresContaining(genre, pageRequest);
        } else if (genres != null && !genres.isEmpty()) {
            return movieRepository.findByGenresAll(genres, pageRequest);
        }

        return movieRepository.findAll(pageRequest);
    }

    public Optional<Movie> getMovieById(ObjectId id){
        return movieRepository.findById(id);
    }

    public Movie createMovie(Movie newMovie) {
        return movieRepository.insert(newMovie);
    }

    public List<Review> getMovieReviews(ObjectId movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);

        if (movie.isPresent()) {
            return movie.get().getReviewIds();
        } else {
            throw new NoSuchElementException("Movie with ID " + movieId + " not found");
        }
    }

    public Movie rateMovie(ObjectId movieId, int rating) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setRating(rating);

            return movieRepository.save(movie);
        } else {
            throw new NoSuchElementException("Movie with ID " + movieId + " not found");
        }
    }
}
