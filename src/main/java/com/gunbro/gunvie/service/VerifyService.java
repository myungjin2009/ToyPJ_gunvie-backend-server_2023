package com.gunbro.gunvie.service;

import com.gunbro.gunvie.model.requestDto.Email;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {

    //세션 유효기간 검사
    public boolean checkExpireDate(Email email) {
        //TODO : 세션의 유효기간 검사하기 (5분)
        if(email == null) {
            return false;
        } else {
            return true;
        }
    }

    //인증번호 일치 불일치 확인
    public boolean verifyEqual(Email existEmail, Email receivedEmail) {
        if(receivedEmail.getVerifyNumber().equals(existEmail.getVerifyNumber())) {
            return true;
        } else {
            return false;
        }
    }
}
