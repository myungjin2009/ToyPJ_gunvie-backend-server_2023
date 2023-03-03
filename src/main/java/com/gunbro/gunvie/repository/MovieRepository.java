package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.model.jpa.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findByRangeDate(LocalDate date);
}
