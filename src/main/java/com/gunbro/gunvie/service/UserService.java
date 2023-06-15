package com.gunbro.gunvie.service;

import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.requestDto.LocalLogin;
import com.gunbro.gunvie.model.requestDto.User.SearchIdRequestDto;
import com.gunbro.gunvie.model.requestDto.User.SearchPwRequestDto;
import com.gunbro.gunvie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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

        //Transactional 메소드에서 Try-Catch문을 사용하면 정상적인 RollBack루틴이 되지 않아 문법오류가 발생한다.
        //TODO:그렇다면 여기서는 예외처리를 어떻게 해야 할까?

//        try{
            userRepository.save(user);
//        }catch(Exception e) {
//            return "ERROR" + e.getMessage();
//        }

        return "SUCCESS";
    }

    @Transactional
    public boolean updatePassword(User user) {
        //비밀번호 암호화 로직 (BCrypt)
        String passwordEncoded = bCryptService.encodeBcrypt(user.getPassword());
        user.setPassword(passwordEncoded);
        userRepository.save(user);
        return true;
    }

    public User findUserById(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public User loginLocalUser(LocalLogin localLogin) {
        User user = userRepository.findByLoginId(localLogin.getLoginId());
        if(user == null) {
            //존재하지 않는 아이디
            return null;
        }

        boolean result = bCryptService.matchesBcrypt(localLogin.getPassword(), user.getPassword());
        if(!result) {
            //비밀번호 불일치
            return null;
        } else {
            return user;
        }

    }

    public User searchId(SearchIdRequestDto searchIdRequestDto) {
        return userRepository.findByNameAndEmail(searchIdRequestDto.getName(), searchIdRequestDto.getEmail());
    }

    public User searchPw(SearchPwRequestDto searchPwRequestDto) {
        return userRepository.findByNameAndLoginIdAndEmail(
                searchPwRequestDto.getName(),
                searchPwRequestDto.getLoginId(),
                searchPwRequestDto.getEmail()
        );
    }

    @Transactional
    public boolean deleteUser(User user) {
        userRepository.delete(user);
        return true;
    }
}
