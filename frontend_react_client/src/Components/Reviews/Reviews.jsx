import "./Reviews.css";
import { useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
import ReviewForm from "../ReviewForm/ReviewForm";
import React from "react";
import axios from "axios";
import MovieCard from "../MovieCard/MovieCard";
import Rating from "../Rating/Rating";
import {BASE_URL}from "../../App.jsx" ;

const Reviews = ({ getMovieData, movie, reviews, setReviews }) => {
  const revText = useRef();
  let params = useParams();
  const movieId = params.movieId;
  useEffect(() => {
    getMovieData(movieId);
  }, []);

  const addReview = async (e) => {
    e.preventDefault();

    const rev = revText.current;

    try {
      axios.post(`${BASE_URL}/api/v1/movies/${movieId}/review`, {
        reviewBody: rev.value,
        imdbId: movieId,
      });

      const updatedReviews = [...reviews, { body: rev.value }];

      rev.value = "";

      setReviews(updatedReviews);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="movie-details">
      <MovieCard movie={movie} />
      <Rating movieId={movie?.id} />
      <Container>
        <Row>
          <Col>
            <h3>Reviews</h3>
          </Col>
        </Row>
        <Row className="mt-2">
          <Col>
            {
              <>
                <Row>
                  <Col>
                    <ReviewForm
                      handleSubmit={addReview}
                      revText={revText}
                      labelText="Write a Review?"
                    />
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <hr />
                  </Col>
                </Row>
              </>
            }
            {reviews?.map((review) => {
              return (
                <>
                  <Row>
                    <Col key={review.id}>{review.body}</Col>
                  </Row>
                  <Row>
                    <Col>
                      <hr />
                    </Col>
                  </Row>
                </>
              );
            })}
          </Col>
        </Row>
        <Row>
          <Col>
            <hr />
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Reviews;
