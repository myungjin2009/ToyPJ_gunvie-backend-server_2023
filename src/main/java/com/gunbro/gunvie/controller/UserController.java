package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.config.enumData.BCrypt;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.jpa.UserTest;
import com.gunbro.gunvie.model.requestDto.Email;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.repository.UserTestRepository;
import com.gunbro.gunvie.service.BCryptService;
import com.gunbro.gunvie.service.MovieService;
import com.gunbro.gunvie.service.UserService;
import com.gunbro.gunvie.service.VerifyService;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cglib.core.Local;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;



    @PostMapping("/join")
    public DefaultDto insertSession(@RequestBody User user, HttpSession httpSession) {
        DefaultDto dto = new DefaultDto();
        Email verifyResult = (Email) httpSession.getAttribute("emailVerify");
        //TODO : 세션 유효기간 검사 서비스 레벨에 작성하기
        if(verifyResult == null) {
            dto.setCode(403);
            dto.setMessage("세션이 지워졌거나 너무 오래되었습니다.");
            return dto;
        }

        //이메일 인증 확인(/email/verify)가 안되었을 경우
        if(!verifyResult.isVerified()) {
            dto.setCode(403);
            dto.setMessage("이메일 인증이 되지 않았습니다.");
            return dto;
        }

        //이메일 인증 확인 후, 이메일 주소가 바뀌었을 경우
        if(!verifyResult.getEmail().equals(user.getEmail())) {
            dto.setCode(403);
            dto.setMessage("이메일 인증 후 이메일주소 변경됨. 데이터 무결성 오류");
            return dto;
        }

        //TODO : 회원정보 DB에 저장하기
        String registerResult = userService.registerUser(user);
        if(registerResult.equals("Login_id_already_exists")) {
            dto.setCode(403);
            dto.setMessage("로그인 아이디가 이미 서버에 존재합니다.");
            return dto;
        }

        if(registerResult.contains("ERROR")) {
            dto.setCode(500);
            dto.setMessage("내부 서버 오류,  " + registerResult);
            return dto;
        }

        if(registerResult.equals("SUCCESS")) {
            dto.setCode(200);
            dto.setMessage("회원가입 완료됨.");
            return dto;
        }

        dto.setCode(500);
        dto.setMessage("내부 서버 오류, 알 수 없는 오류");
        return dto;
    }
}
