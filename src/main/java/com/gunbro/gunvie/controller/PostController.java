package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.model.jpa.User;

import com.gunbro.gunvie.model.requestDto.Post.AddPostRequestDto;
import com.gunbro.gunvie.model.responseDto.Post.AddPostResponseDto;
import com.gunbro.gunvie.service.EstimateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private EstimateService estimateService;

    @PostMapping("/add_review")
    public AddPostResponseDto addReview(@RequestBody AddPostRequestDto addPostRequestDto, HttpSession httpSession) {
        AddPostResponseDto dto = new AddPostResponseDto();
        User user = (User)httpSession.getAttribute("loginSession");
        if (user == null) {
            dto.setCode(400);
            dto.setMessage("로그인이 필요합니다.");

            return dto;
        }
            
        int result = estimateService.addReview(
                user,
                addPostRequestDto.getMovieId(),
                addPostRequestDto.getText(),
                addPostRequestDto.getStartRating());

        if(result == 1) {
            dto.setCode(400);
            dto.setMessage("해당 id의 영화는 존재하지 않습니다. movieId를 확인해주세요");
            return dto;
        }

        dto.setCode(200);
        dto.setMessage("정상적으로 등록 되었습니다.");
        return dto;
    }
}
