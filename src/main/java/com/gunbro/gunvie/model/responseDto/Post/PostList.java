package com.gunbro.gunvie.model.responseDto.Post;

import com.gunbro.gunvie.model.jpa.Estimate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostList {

    private String userName;
    private String userLoginId;
    private int startRating;
    private String movieName;
    private LocalDateTime createdAt;
    private String content;
    private String imgPath;

    public PostList(Estimate estimate) {
        this.userName = estimate.getEstimate().getUser().getName();
        this.userLoginId = estimate.getEstimate().getUser().getLoginId();
        this.startRating = estimate.getStartRating();
        this.movieName = estimate.getEstimate().getMovie().getName();
        this.createdAt = estimate.getCreatedAt();
        this.content = estimate.getText();
        this.imgPath = estimate.getImages();
    }
}
