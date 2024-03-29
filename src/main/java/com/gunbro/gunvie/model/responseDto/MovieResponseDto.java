package com.gunbro.gunvie.model.responseDto;

import com.gunbro.gunvie.model.jpa.Movie;

import java.util.List;

public class MovieResponseDto extends DefaultDto {

    private List<Movie> movieData;

    public MovieResponseDto() {
    }

    public List<Movie> getMovieData() {
        return movieData;
    }

    public void setMovieData(List<Movie> movieData) {
        this.movieData = movieData;
    }
}
