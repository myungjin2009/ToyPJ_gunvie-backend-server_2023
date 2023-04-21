package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.config.enumData.FileType;
import com.gunbro.gunvie.model.jpa.Estimate;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.requestDto.Post.AddPostRequestDto;
import com.gunbro.gunvie.model.responseDto.Post.AddImageResponseDto;
import com.gunbro.gunvie.model.responseDto.Post.AddPostResponseDto;
import com.gunbro.gunvie.model.responseDto.Post.PostList;
import com.gunbro.gunvie.model.responseDto.Post.PostListResponseDto;
import com.gunbro.gunvie.module.FileCheck;
import com.gunbro.gunvie.service.EstimateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public AddImageResponseDto uploadImage(@RequestParam(value = "post_id") Long movieId ,
                                           @RequestParam(value = "files") MultipartFile multipartFile,
                                           HttpSession httpSession) throws IOException {
        //TODO 파일 용량 초과시 예외처리
        //TODO 잘못된 파일 수신 시 예외처리 (org.springframework.web.multipart.support.MissingServletRequestPartException: Required part 'files' is not present.)
        AddImageResponseDto dto = new AddImageResponseDto();
        //이미지 이름에 중복방지 UUID를 추가하여 DB에 저장
        //그와 동시에 이미지 파일은 서버에 저장 (multipartFile.transferTo)

        User user = (User)httpSession.getAttribute("loginSession");
        if(user == null) {
            dto.setCode(403);
            dto.setMessage("로그인이 되어있지 않습니다.");
            return dto;
        }

        Estimate estimate = estimateService.findReviewByMovieId(user, movieId);
        if (estimate == null) {
            dto.setCode(403);
            dto.setMessage("해당유저가 작성한 리뷰 글이 없습니다.");
            return dto;
        }

        //이미지 검사
        FileCheck fileCheck = new FileCheck();
        if(!fileCheck.imageCheck(multipartFile)){
            dto.setCode(403);
            dto.setMessage("업로드 된 파일은 이미지 형식이 아닙니다.");
            return dto;
        }

        String fvConvert = fileCheck.convertUniqueName(multipartFile.getOriginalFilename());
        Estimate newEstimate = estimateService.saveImage(estimate, fvConvert);
        if(newEstimate == null) {
            dto.setCode(500);
            dto.setMessage("무언가 잘못되었습니다.");
            return dto;
        }
        String saveFileResult = fileCheck.saveFile(multipartFile, newEstimate.getImages(), FileType.POSTIMAGE);
        //TODO 중복코드
        if(saveFileResult == null) {
            dto.setCode(500);
            dto.setMessage("무언가 잘못되었습니다.");
            return dto;
        }

        dto.setCode(201);
        dto.setMessage("이미지가 저장 되었습니다.");
        dto.setFilePath(fvConvert);
        return dto;

    }



    @GetMapping("/fetch")
    public PostListResponseDto getEstimateData(@RequestParam String date, @RequestParam int rank) {
        PostListResponseDto dto = new PostListResponseDto();
        List<Estimate> estimates = estimateService.getReview(date,rank);
        if (estimates == null) {
            dto.setCode(200);
            dto.setMessage("해당하는 값이 없습니다.");
            return dto;
        }

        //TODO Stream 으로 코드 리팩토링 하기..
        List<PostList> postListList = new ArrayList<>();
        if (!estimates.isEmpty()) {
            for (Estimate estimate : estimates) {
                postListList.add(new PostList(estimate));
            }
        }
        dto.setCode(200);
        dto.setMessage("정상적으로 불러왔습니다.");
        dto.setPostLists(postListList);
        return dto;
    }
}
