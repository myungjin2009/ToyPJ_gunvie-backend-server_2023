package com.gunbro.gunvie.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptService {

    public String encodeBcrypt(String plainText, int strength) {
        return new BCryptPasswordEncoder(strength).encode(plainText);
    }

    public boolean matchesBcrypt(String plainText, String hashValue, int strength) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        return passwordEncoder.matches(plainText, hashValue);
    }
}
