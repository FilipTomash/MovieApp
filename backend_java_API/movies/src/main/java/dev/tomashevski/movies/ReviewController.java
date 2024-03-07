package dev.tomashevski.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @GetMapping
    public  ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<>(reviewService.allReviews(), HttpStatus.OK);
    }
}
