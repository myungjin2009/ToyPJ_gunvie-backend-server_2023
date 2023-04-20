package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.model.jpa.Movie;
import com.gunbro.gunvie.model.responseDto.MovieResponseDto;
import com.gunbro.gunvie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;


    //API : /movie/fetch?date='날짜String'
    @GetMapping("/fetch")
    public MovieResponseDto fetch(@RequestParam String date) {
        List<Movie> result = movieService.getMovieData(date);
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        if (result.isEmpty()) {
            movieResponseDto.setCode(500);
            movieResponseDto.setMessage("데이터를 불러오는데 실패하였습니다. 서버측 문제입니다.");
        } else {
            movieResponseDto.setCode(200);
            movieResponseDto.setMessage("데이터를 정상적으로 불러왔습니다.");
            movieResponseDto.setMovieData(result);
        }

        return movieResponseDto;
    }
}
