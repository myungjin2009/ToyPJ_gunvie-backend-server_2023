package com.gunbro.gunvie.service;


import com.gunbro.gunvie.config.enumData.FileType;
import com.gunbro.gunvie.model.jpa.Estimate;
import com.gunbro.gunvie.model.jpa.EstimateEmbed;
import com.gunbro.gunvie.model.jpa.Movie;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.requestDto.Post.AddEditPostRequestDto;
import com.gunbro.gunvie.module.FileCheck;
import com.gunbro.gunvie.module.Parse;
import com.gunbro.gunvie.repository.EstimateRepository;
import com.gunbro.gunvie.repository.MovieRepository;
import com.gunbro.gunvie.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public boolean editEstimate(AddEditPostRequestDto addEditPostRequestDto, User user) {
        //TODO 중복코드!
        Movie movie = movieRepository.findById(addEditPostRequestDto.getMovieId()).orElse(null);
        if(movie == null) return false;
        Estimate estimate = estimateRepository.findReviewByMovieId(user, movie);
        if(estimate == null) return false;

        if(addEditPostRequestDto.getText() != null || !addEditPostRequestDto.getText().isEmpty()) {
            estimate.setText(addEditPostRequestDto.getText());
        }
        if(addEditPostRequestDto.getStartRating() != 0 || addEditPostRequestDto.getStartRating() == estimate.getStartRating()) {
            estimate.setStartRating(addEditPostRequestDto.getStartRating());
        }
        estimateRepository.save(estimate);
        return true;
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

    public Page<Estimate> showUserReview(User user, int page) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Direction.DESC,"createdAt"));
        return estimateRepository.findByUser(user, pageable);
    }

    public List<Estimate> getReview(String date, int rank) {
        LocalDate convertDate = Parse.StringToLocalDateParse(date);
        Movie movie = movieRepository.findByRangeDateAndRankMv(convertDate, rank);
        if(movie == null) return null;
        return estimateRepository.findByMovieId(movie);
    }

    @Transactional
    public String deleteImage(User user, Long movieId){
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if(movie == null) return "";
        Estimate estimate = estimateRepository.findReviewByMovieId(user, movie);
        if(estimate == null) return "";

        String fileName = "";
        if(estimate.getImages() != null) {
            fileName = estimate.getImages();
            //사진 파일 삭제
            FileCheck fileCheck = new FileCheck();
            boolean result = fileCheck.deleteFile(estimate.getImages(), FileType.POSTIMAGE);
            //사진 경로 삭제
            if (result) {
                estimate.setImages(null);
            }
            estimateRepository.save(estimate);
        }
        return fileName;
    }
}
