import "./App.css";
import { useState, useEffect } from "react";
import Layout from "./Components/Layout";
import { Routes, Route } from "react-router-dom";
import Home from "./Components/Home/Home";
import Header from "./Components/Header/Header";
import Reviews from "./Components/Reviews/Reviews";
import NotFound from "./Components/NotFound/NotFound";
import axios from "axios";

export const BASE_URL = "http://localhost:8080";

function App() {
  const [movies, setMovies] = useState(null);
  const [movie, setMovie] = useState();
  const [reviews, setReviews] = useState([]);
  const [searchValue, setSearchValue] = useState("");
  const [errorMsg, setErrorMsg] = useState(null);
  const [isSearching, setIsSearching] = useState(false);

  const getMovies = async () => {
    try {
      axios.get(`${BASE_URL}/api/v1/movies`).then((response) => {
        setMovies(response.data.content);
        setErrorMsg(null);
      });
      return;
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    setMovies(null);
    setErrorMsg(null);

    if (!searchValue) {
      getMovies();
      setIsSearching(false);
      return;
    }

    axios
      .get(`${BASE_URL}/api/v1/movies?title=${searchValue}`)
      .then((value) => {

        setMovie(value.data.content);
        setIsSearching(true);
        setErrorMsg(null);
      })
      .catch(() => {
        setErrorMsg("No movies found, please try again!");
      });
  }, [searchValue]);

  const updateSearchValue = (newValue) => {
    setSearchValue(newValue);
  };

  // ADDED CODE

  const getMovieData = async (movieId) => {
    try {
      axios.get(`${BASE_URL}/api/v1/movies/${movieId}`).then((response) => {
        const singleMovie = response.data;

        setMovie(singleMovie);

        setReviews(singleMovie?.reviewIds);
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="App">
      <Header setIsSearching={setIsSearching} />
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route
            path="/"
            element={
              <Home
                movies={movies}
                updateSearchValue={updateSearchValue}
                errorMsg={errorMsg}
                movie={movie}
                isSearching={isSearching}
              />
            }
          />
          <Route
            path="/Reviews/:movieId"
            element={
              <Reviews
                getMovieData={getMovieData}
                movie={movie}
                reviews={reviews}
                setReviews={setReviews}
              />
            }
          ></Route>
          <Route path="*" element={<NotFound />}></Route>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
