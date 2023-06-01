package com.gunbro.gunvie.module.randomNumber;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 6자리 랜덤한 숫자를 발생 반환해주는 심플한 클래스이다.
 * 이메일 인증, 아이디 찾기 등 메일 인증을 위한 인증번호 기능에 사용된다.
 */
@Component
public class SimpleRandomNumberGenerator implements RandomNumberGenerator{

    @Override
    public String generate(int digit) {
        if(digit <= 0 || digit > 8) throw new IllegalArgumentException("digit 값의 범위가 초과 되었습니다.");

        int min = (int) Math.pow(10, digit - 1);
        int max = (int) Math.pow(10, digit);
        Random random = new Random();
        int result = random.nextInt(max - min) + min;
        return String.valueOf(result);
    }
}
