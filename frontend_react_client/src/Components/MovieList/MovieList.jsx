import "./MovieList.css";
import React from "react";
import MovieCard from "../MovieCard/MovieCard";

const MovieList = ({ movies, errorMsg }) => {
  return (
    <div className="movie-list">
      {!movies && !errorMsg && <h3>Loading...</h3>}
      <div className="movie">
        {movies &&
          !errorMsg &&
          movies?.map((movie) => <MovieCard key={movie.id} movie={movie} />)}
      </div>
    </div>
  );
};

export default MovieList;
