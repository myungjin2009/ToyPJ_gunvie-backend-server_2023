package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.model.jpa.User;

import com.gunbro.gunvie.model.requestDto.Post.AddPostRequestDto;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.model.responseDto.Post.AddPostResponseDto;
import com.gunbro.gunvie.module.FileValidationCheck;
import com.gunbro.gunvie.service.EstimateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @PostMapping("/add_img")
    public void uploadImage(HttpSession httpSession) throws IOException {
        //세션에서 유저정보 불러오기
        //본인이 아니면 거절하고, 맞으면
        //dto에서 request한 estimate 객체 가져오기
        //거기따가 images 경로 집어넣기
        //그와 동시에 이미지는 서버에 저장 (multipartFile.transferTo)

//        //TODO 파일 용량 초과시 예외처리
//        DefaultDto dto = new DefaultDto();
//
//        //이미지 검사
//        FileValidationCheck fvCheck = new FileValidationCheck();
//        if(!fvCheck.imageCheck(multipartFiles)){
//            dto.setCode(403);
//            dto.setMessage("업로드 된 파일들 중, 올바른 이미지 형식이 아닌 파일이 존재함.");
//        }
//
//        for (MultipartFile multipartFile : multipartFiles) {
//
//        }
//
//        return "모두 사진";
    }
}
