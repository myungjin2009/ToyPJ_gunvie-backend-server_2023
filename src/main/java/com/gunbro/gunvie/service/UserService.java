package com.gunbro.gunvie.service;

import com.gunbro.gunvie.model.jpa.Follow;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.requestDto.LocalLogin;
import com.gunbro.gunvie.model.requestDto.User.SearchIdRequestDto;
import com.gunbro.gunvie.repository.FollowRepository;
import com.gunbro.gunvie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptService bCryptService;



    @Transactional
    public String registerUser(User user) {
        User result = userRepository.findByLoginId(user.getLoginId());
        if(result != null) {
            return "Login_id_already_exists";
        }

        //비밀번호 암호화 로직 (BCrypt)
        String passwordEncoded = bCryptService.encodeBcrypt(user.getPassword());
        user.setPassword(passwordEncoded);

        //Try-Catch문을 사용하면 정상적인 RollBack루틴이 되지 않아 문법오류가 발생한다.
        //TODO:그렇다면 여기서는 예외처리를 어떻게 해야 할까?

//        try{
            userRepository.save(user);
//        }catch(Exception e) {
//            return "ERROR" + e.getMessage();
//        }

        return "SUCCESS";
    }

    public User loginLocalUser(LocalLogin localLogin) {
        User user = userRepository.findByLoginId(localLogin.getLoginId());
        if(user == null) {
            System.out.println("아이디 없음!");
            return null;
        }

        boolean result = bCryptService.matchesBcrypt(localLogin.getPassword(), user.getPassword());
        if(!result) {
            System.out.println("비밀번호 안맞음!");
            return null;
        } else {
            return user;
        }

    }

    public User searchId(SearchIdRequestDto searchIdRequestDto) {
        User user = userRepository.findByNameAndEmail(searchIdRequestDto.getName(), searchIdRequestDto.getEmail());
        return user;
    }
}
