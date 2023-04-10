package com.gunbro.gunvie.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Estimate {

    @Embedded
    @Id
    private EstimateEmbed estimate;

    @Column(name = "startRating",
            columnDefinition = "INT DEFAULT 0 CHECK (startRating >= 0 AND startRating <= 5)")
    private int startRating;

    @Column
    private String text;

    @Column
    private String Img;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;
}
