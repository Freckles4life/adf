package com.project.adf.model;

public class Movies {
    private String MovieName;
    private String MovieId;
    private String MovieCategory;
    private String MovieDescription;
    private String MovieRating;

    public Movies(String movieName, String movieId, String movieCategory, String movieDescription, String movieRating) {
        MovieName = movieName;
        MovieId = movieId;
        MovieCategory = movieCategory;
        MovieDescription = movieDescription;
        MovieRating = movieRating;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getMovieCategory() {
        return MovieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        MovieCategory = movieCategory;
    }

    public String getMovieDescription() {
        return MovieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        MovieDescription = movieDescription;
    }

    public String getMovieRating() {
        return MovieRating;
    }

    public void setMovieRating(String movieRating) {
        MovieRating = movieRating;
    }
}
