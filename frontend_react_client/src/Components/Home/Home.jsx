import "./Home.css";
import MovieCard from "../MovieCard/MovieCard";
import MovieList from "../MovieList/MovieList";
import { useState, useEffect } from "react";

const Home = ({ movies, updateSearchValue, errorMsg, movie, isSearching }) => {
  const [value, setValue] = useState("");

  useEffect(() => {
    const timer = setTimeout(() => {
      updateSearchValue(value);
    }, 1000);

    return () => {
      clearTimeout(timer);
    };
  }, [value]);

  return (
    <div className="movie-list">
      <div className="SearchInput">
        <input
          type="text"
          placeholder="Search by title..."
          value={value}
          onChange={(e) => setValue(e.target.value)}
        />
      </div>
      <h1>Movies: </h1>
      {!isSearching ? (
        <MovieList movies={movies} errorMsg={errorMsg} />
      ) : (
        <MovieCard movie={movie[0]} />
      )}
   </div>
  );
};

export default Home;
