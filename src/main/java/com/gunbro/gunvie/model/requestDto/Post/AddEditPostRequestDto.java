package com.gunbro.gunvie.model.requestDto.Post;

public class AddEditPostRequestDto {
    private Long movieId;

    private String text;

    private int startRating;


    public Long getMovieId() {
        return movieId;
    }

    public String getText() {
        return text;
    }

    public int getStartRating() {
        return startRating;
    }
}
