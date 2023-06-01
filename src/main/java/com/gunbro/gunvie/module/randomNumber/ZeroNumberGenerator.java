package com.gunbro.gunvie.module.randomNumber;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ZeroNumberGenerator implements RandomNumberGenerator {
    @Override
    public String generate(int digit) {
        if(digit <= 0 || digit > 8) throw new IllegalArgumentException("digit 값의 범위가 초과 되었습니다.");

        String result = "";
        for(int i = 0; i < digit; i++) {
            result += "0";
        }
        return result;

    }
}
