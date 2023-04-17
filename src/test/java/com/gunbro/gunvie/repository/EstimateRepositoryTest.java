package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.model.jpa.Estimate;
import com.gunbro.gunvie.model.jpa.EstimateEmbed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EstimateRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Test
    void addReviewTest() {
        EstimateEmbed estimateEmbed1 = new EstimateEmbed();
        estimateEmbed1.setMovie(movieRepository.findByName("대외비"));
        estimateEmbed1.setUser(userRepository.findByLoginId("myungjin2009"));

        Estimate estimate1 = new Estimate();
        estimate1.setEstimate(estimateEmbed1);
        estimate1.setText("진짜 노잼!!");
        estimate1.setStartRating(1);
        estimate1.setImages("aaa.png");

        estimateRepository.save(estimate1);
    }
}