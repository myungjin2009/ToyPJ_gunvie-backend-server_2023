package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.config.enumData.EmailType;
import com.gunbro.gunvie.model.jpa.Follow;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.requestDto.Email;
import com.gunbro.gunvie.model.requestDto.LocalLogin;
import com.gunbro.gunvie.model.requestDto.User.SearchIdRequestDto;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.model.responseDto.FollowUser.FollowUserList;
import com.gunbro.gunvie.model.responseDto.FollowUser.FollowUserResponseDto;
import com.gunbro.gunvie.model.responseDto.User.SearchIdResponseDto;
import com.gunbro.gunvie.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    //temp
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @GetMapping("/following")
    public FollowUserResponseDto showFollowing(@RequestParam int page, HttpSession httpSession) {
        FollowUserResponseDto dto = new FollowUserResponseDto();

        User user2 = (User)httpSession.getAttribute("loginSession");
        if (user2 == null) {
            dto.setCode(400);
            dto.setMessage("로그인이 필요합니다.");
            dto.setList(null);
            return dto;
        }

        List<FollowUserList> followUserLists = new ArrayList<>();
        Page<Follow> result = followService.showFollowingUsers(page, user2);
        for (Follow f : result.getContent()) {
            followUserLists.add(new FollowUserList(f));
        }

        dto.setCode(200);
        dto.setMessage("정상적으로 불러왔습니다. (최근 추가된 순서대로)");
        dto.setList(followUserLists);

        return dto;
    }

    @PostMapping("/join")
    public DefaultDto userJoin(@RequestBody User user, HttpSession httpSession) {
        DefaultDto dto = new DefaultDto();
        Email verifyResult = (Email) httpSession.getAttribute("emailVerify"+ EmailType.VERIFY_NUMBER.name());
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

    @PostMapping("/search_id")
    public SearchIdResponseDto searchId(@RequestBody SearchIdRequestDto searchIdRequestDto, HttpSession httpSession) {
        SearchIdResponseDto dto = new SearchIdResponseDto();
        Email verifyResult = (Email) httpSession.getAttribute("emailVerify"+ EmailType.FIND_ID.name());
        //TODO : 세션 유효기간 검사 서비스 레벨에 작성하기
        //TODO : 중복 코드!
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
        if (!verifyResult.getEmail().equals(searchIdRequestDto.getEmail())) {
            dto.setCode(403);
            dto.setMessage("이메일 인증 후 이메일주소 변경됨. 데이터 무결성 오류");
            return dto;
        }


        User user = userService.searchId(searchIdRequestDto);
        if(user == null) {
            dto.setCode(400);
            dto.setMessage("이름과 이메일에 해당하는 유저 아이디가 없습니다.");
            return dto;
        }

        dto.setCode(200);
        dto.setMessage("DB에서 검색된 로그인 아이디를 반환합니다.");
        dto.setLoginId(user.getLoginId());

        //httpSession.invalidate();

        return dto;
    }
}
