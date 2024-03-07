import "./Rating.css";
import React, { useState } from "react";
import axios from "axios";
import { BASE_URL } from "../../App";

const Rating = ({ movieId }) => {
  const [selectedRating, setSelectedRating] = useState(0);

  const handleRatingClick = async (ratingValue) => {
    try {
      const response = await axios.post(
        `${BASE_URL}/api/v1/movies/${movieId}/rate`,
        {
          rating: ratingValue,
        }
      );
      console.log("Rating added successfully:", response.data);
      setSelectedRating(ratingValue);
    } catch (error) {
      console.error("Error adding rating:", error);
    }
  };

  return (
    <div className="rating-container">
      {[...Array(10)].map((_, index) => (
        <span
          key={index}
          className={`star ${index < selectedRating ? "filled" : ""}`}
          onClick={() => handleRatingClick(index + 1)}
        >
          &#9733;
        </span>
      ))}
    </div>
  );
};

export default Rating;
