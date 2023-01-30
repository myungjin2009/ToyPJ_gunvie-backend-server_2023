package com.gunbro.gunvie.service;

import com.gunbro.gunvie.config.enumData.BCrypt;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptService bCryptService;

    @Transactional
    public String registerUser(User user) {
        List<User> result = userRepository.findByLoginId(user.getLoginId());
        if(!result.isEmpty()) {
            return "Login_id_already_exists";
        }

        //비밀번호 암호화 로직 (BCrypt)
        String passwordEncoded = bCryptService.encodeBcrypt(user.getPassword(), BCrypt.STRENGTH.getStrength());
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
}
