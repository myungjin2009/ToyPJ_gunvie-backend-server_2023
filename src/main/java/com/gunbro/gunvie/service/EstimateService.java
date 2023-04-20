package com.gunbro.gunvie.service;


import com.gunbro.gunvie.model.jpa.Estimate;
import com.gunbro.gunvie.model.jpa.EstimateEmbed;
import com.gunbro.gunvie.model.jpa.Movie;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.module.Parse;
import com.gunbro.gunvie.repository.EstimateRepository;
import com.gunbro.gunvie.repository.MovieRepository;
import com.gunbro.gunvie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EstimateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    public int addReview(User user, Long movieId, String text, int startRating) {
        int result = 0;

        EstimateEmbed estimateEmbed1 = new EstimateEmbed();
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if(movie == null) {
            return 1;   //id에 해당되는 영화가 없음
        }
        estimateEmbed1.setMovie(movie);
        estimateEmbed1.setUser(user);

        Estimate estimate1 = new Estimate();
        estimate1.setEstimate(estimateEmbed1);
        estimate1.setText(text);
        estimate1.setStartRating(startRating);

        estimateRepository.save(estimate1);
        return result;
    }

    public Estimate findReviewByMovieId(User user, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        return estimateRepository.findReviewByMovieId(user, movie);
    }

    public Estimate saveImage(Estimate estimate, String convertFileName) {
        estimate.setImages(convertFileName);
        Estimate newEstimate = estimateRepository.save(estimate);
        if(!newEstimate.getImages().equals(convertFileName)) return null;
        return newEstimate;
    }

    public Page<Estimate> showMyReview(User user, int page) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Direction.DESC,"createdAt"));
        return estimateRepository.findByUser(user, pageable);
    }

    public List<Estimate> getReview(String date, int rank) {
        LocalDate convertDate = Parse.StringToLocalDateParse(date);
        Movie movie = movieRepository.findByRangeDateAndRankMv(convertDate, rank);
        if(movie == null) return null;
        return estimateRepository.findByMovieId(movie);
    }
}
