package com.gunbro.gunvie.model.jpa;

import jakarta.persistence.*;

@Embeddable
public class EstimateEmbed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="movieId")
    private Movie movie;
}
