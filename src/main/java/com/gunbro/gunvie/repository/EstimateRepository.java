package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.model.jpa.Estimate;
import com.gunbro.gunvie.model.jpa.EstimateEmbed;
import com.gunbro.gunvie.model.jpa.Movie;
import com.gunbro.gunvie.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, EstimateEmbed> {

    @Query("SELECT u FROM Estimate u WHERE u.estimate.movie = :movie AND u.estimate.user = :user")
    Estimate findReviewByMovieId(@Param("user") User user, @Param("movie") Movie movie);

}
