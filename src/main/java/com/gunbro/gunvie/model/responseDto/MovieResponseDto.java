package com.gunbro.gunvie.model.responseDto;

import com.gunbro.gunvie.model.jpa.Movie;

import java.util.List;

public class MovieResponseDto {

    private int code;

    private String message;

    private List<Movie> movieData;

    public MovieResponseDto() {
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Movie> getMovieData() {
        return movieData;
    }

    public void setMovieData(List<Movie> movieData) {
        this.movieData = movieData;
    }
}
