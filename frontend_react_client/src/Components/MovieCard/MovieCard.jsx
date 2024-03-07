import React from "react";
import "./MovieCard.css";
import { useNavigate } from "react-router-dom";
import Rating from "../Rating/Rating";

const MovieCard = ({ movie }) => {
  const navigate = useNavigate();


  return (
    <>
    <div className="MovieCard" onClick={() => navigate(`/Reviews/${movie.id}`)}>
      <h2>{movie?.title}</h2>
      <p>{movie?.description}</p>
      <p>Year: {movie?.year}</p>
      <p>Rating: {movie?.rating.toFixed(2)} / 10</p>
    
    </div>
    <img src={movie?.poster} alt="movie-poster" onClick={() => navigate(`/Reviews/${movie.id}`)}/>
    </>
  );
};

export default MovieCard;
