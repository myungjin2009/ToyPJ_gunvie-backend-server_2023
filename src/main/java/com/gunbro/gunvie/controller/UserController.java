package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.config.enumData.BCrypt;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.jpa.UserTest;
import com.gunbro.gunvie.repository.UserTestRepository;
import com.gunbro.gunvie.service.BCryptService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserTestRepository userTestRepository;
    @Autowired
    BCryptService bCryptService;

    @GetMapping("/hello")
    public String hello(){
        UserTest userTest = new UserTest();
        userTest.setLoginId("hello1");
        String afterEncode = bCryptService.encodeBcrypt("1234", BCrypt.STRENGTH.getStrength());
        userTest.setPassword(afterEncode);
        userTestRepository.save(userTest);

        List<UserTest> findUser = userTestRepository.findByloginId("hello1");
        boolean result = bCryptService.matchesBcrypt("1235", findUser.get(0).getPassword(), BCrypt.STRENGTH.getStrength());

        return String.valueOf(result);
    }

    @GetMapping("/session")
    public String insertSession(HttpSession httpSession) {

        if(httpSession.getAttribute("principal2") == null) {
            System.out.println("세션이 null 입니다. 새로 발급합니다.");
            httpSession.setAttribute("principal2", "wtf");
        } else {
            System.out.println("세션이 이미 존재합니다.");
            System.out.println(httpSession.getAttribute("principal2"));
        }

        return "TRUE";
    }
}
