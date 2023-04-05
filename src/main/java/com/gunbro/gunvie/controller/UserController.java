package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.config.enumData.Gender;
import com.gunbro.gunvie.model.jpa.Follow;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.requestDto.Email;
import com.gunbro.gunvie.model.requestDto.LocalLogin;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.model.responseDto.FollowUserResponseDto;
import com.gunbro.gunvie.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @GetMapping("/following")
    public List<FollowUserResponseDto> showFollowing(@RequestParam int page, HttpSession httpSession) {


        User user2 = (User)httpSession.getAttribute("loginSession");

        Page<Follow> result = followService.showFollowingUsers(page, user2);
//        List<User> test2 = result.getContent().stream()
//                .map(data -> data.getFollowId().getFollowing()).toList();
//
//        List<FollowUserResponseDto> convertResult = new ArrayList<>();
//
//        for (User u : test2) {
//            convertResult.add(new FollowUserResponseDto(u.getId(),u.getName(), u.getSnsId(), u.getGender(), u.getEmail(), u.getImg()));
//        }
        List<FollowUserResponseDto> convertResult = result.getContent().stream()
                .map(data -> data.getFollowId())
                .map(data2 -> data2.getFollowing())
                .map(user -> new FollowUserResponseDto(user.getId(), user.getName(), user.getSnsId(), user.getGender(), user.getEmail(), user.getImg()))
                .collect(Collectors.toList());



        return convertResult;
    }

    @PostMapping("/join")
    public DefaultDto userJoin(@RequestBody User user, HttpSession httpSession) {
        DefaultDto dto = new DefaultDto();
        Email verifyResult = (Email) httpSession.getAttribute("emailVerify");
        //TODO : 세션 유효기간 검사 서비스 레벨에 작성하기
        if (verifyResult == null) {
            dto.setCode(403);
            dto.setMessage("세션이 지워졌거나 너무 오래되었습니다.");
            return dto;
        }

        //이메일 인증 확인(/email/verify)가 안되었을 경우
        if (!verifyResult.isVerified()) {
            dto.setCode(403);
            dto.setMessage("이메일 인증이 되지 않았습니다.");
            return dto;
        }

        //이메일 인증 확인 후, 이메일 주소가 바뀌었을 경우
        if (!verifyResult.getEmail().equals(user.getEmail())) {
            dto.setCode(403);
            dto.setMessage("이메일 인증 후 이메일주소 변경됨. 데이터 무결성 오류");
            return dto;
        }

        //TODO : 회원정보 DB에 저장하기
        String registerResult = userService.registerUser(user);
        if (registerResult.equals("Login_id_already_exists")) {
            dto.setCode(403);
            dto.setMessage("로그인 아이디가 이미 서버에 존재합니다.");
            return dto;
        }

        if (registerResult.contains("ERROR")) {
            dto.setCode(500);
            dto.setMessage("내부 서버 오류,  " + registerResult);
            return dto;
        }

        if (registerResult.equals("SUCCESS")) {
            dto.setCode(200);
            dto.setMessage("회원가입 완료됨.");
            return dto;
        }

        dto.setCode(500);
        dto.setMessage("내부 서버 오류, 알 수 없는 오류");
        return dto;
    }


    @PostMapping("/login")
    public DefaultDto userLogin(@RequestBody LocalLogin localLogin, HttpServletRequest request) {
        DefaultDto dto = new DefaultDto();

        if(localLogin.getLoginId() == null || localLogin.getPassword() == null) {
            dto.setCode(403);
            dto.setMessage("아이디 또는 비밀번호 누락");
            return dto;
        }

        User user = userService.loginLocalUser(localLogin);
        if (user == null) {
            dto.setCode(403);
            dto.setMessage("아이디 또는 비밀번호가 맞지 않습니다.");
            return dto;
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginSession", user);

        dto.setCode(200);
        dto.setMessage("로그인 되었습니다.");
        return dto;
    }
}
