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

    @Column
    //TODO 0~5 경계값 지정 필요.. 지금은 방법을 모르겠음
    private int startRating;

    @Column
    private String text;

    @Column
    private String images;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    public Estimate() {
        //TODO 이렇게하면 update 해도 createAt 같이 수정됨? 코드 수정 필요.
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public EstimateEmbed getEstimate() {
        return estimate;
    }

    public void setEstimate(EstimateEmbed estimate) {
        this.estimate = estimate;
    }

    public int getStartRating() {
        return startRating;
    }

    public void setStartRating(int startRating) {
        this.startRating = startRating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
