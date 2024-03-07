package dev.tomashevski.movies;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String title;
    private String description;
    private Integer year;
    private List<String> genres;
    private String poster;
    @Getter
    @DocumentReference
    private List<Review> reviewIds;

    private List<Integer> ratings;
    private double rating;

    public Movie(String title, String description, Integer year, List<String> genres, List<Review> reviewIds,List<Integer> ratings, String poster) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.genres = genres;
        this.reviewIds = reviewIds;
        this.ratings=ratings;
        this.poster = poster;
        updateAverageRating();
    }
    public double calculateAverageRating() {
        if (ratings != null && !ratings.isEmpty()) {
            int totalRating = 0;

            for (Integer rating : ratings) {
                totalRating += rating;
            }

            return (double) totalRating / ratings.size();
        } else {
            return 0.0;
        }
    }
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 10) {
            if (ratings == null) {
                ratings = new ArrayList<>();
            }
            ratings.add(rating);
            updateAverageRating();
        } else {
            throw new IllegalArgumentException("Invalid rating. Rating must be between 1 and 10.");
        }
    }

    private void updateAverageRating() {
        rating = calculateAverageRating();
    }
}
