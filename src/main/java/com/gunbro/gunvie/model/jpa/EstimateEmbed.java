package com.gunbro.gunvie.model.jpa;

import jakarta.persistence.*;

@Embeddable
public class EstimateEmbed {

    //TODO 작동을 안함. (Column 'id' cannot be null
    //TODO JPA공부 후, 수정 필요
//    @Column
//    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="movieId")
    private Movie movie;

    public EstimateEmbed() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
