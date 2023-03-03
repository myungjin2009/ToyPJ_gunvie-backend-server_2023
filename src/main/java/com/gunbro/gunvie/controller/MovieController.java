package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.model.jpa.Movie;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.model.responseDto.MovieResponseDto;
import com.gunbro.gunvie.module.API;
import com.gunbro.gunvie.service.MovieService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;


    //API : /movie/fetch?date='날짜String’
    @GetMapping("/fetch")
    public MovieResponseDto fetch(@RequestParam String date) {
        List<Movie> result = movieService.getMovieData(date);

        MovieResponseDto movieResponseDto = new MovieResponseDto();
        if(result == null) {
            movieResponseDto.setCode(500);
            movieResponseDto.setMessage("데이터를 불러오는데 실패하였습니다.");
        } else {
            movieResponseDto.setCode(200);
            movieResponseDto.setMessage("데이터를 정상적으로 불러왔습니다.");
            movieResponseDto.setMovieData(result);
        }

        return movieResponseDto;
    }

}
