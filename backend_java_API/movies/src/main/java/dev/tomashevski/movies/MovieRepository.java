package dev.tomashevski.movies;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {

    @Query("{'title': ?0}")
    Page<Movie> findByTitle(String title, Pageable pageable);
    @Query("{'genres': ?0}")
    Page<Movie> findByGenresContaining(String genre, Pageable pageable);

    @Query("{'genres': {'$all': ?0}}")
    Page<Movie> findByGenresAll(List<String> genres, Pageable pageable);

    @Query("{'year': ?0}")
    Page<Movie> findByReleaseYear(int year, Pageable pageable);

    @Query("{'year': {'$gte': ?0, '$lte': ?1}}")
    Page<Movie> findByReleaseYearBetween(int yearFrom, int yearTo, Pageable pageable);

}
