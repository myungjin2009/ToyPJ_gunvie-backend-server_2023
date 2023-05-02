package com.gunbro.gunvie.model.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Follow {

    @Embedded
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private FollowEmbed followId;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;


    public Follow() {
        this.createdAt = LocalDateTime.now();
    }

    public FollowEmbed getFollowId() {
        return followId;
    }

    public void setFollowId(FollowEmbed followId) {
        this.followId = followId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
