package com.gunbro.gunvie.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptService {

    @Value("${bcrypt.strength}")
    int bcryptStrength;

    public String encodeBcrypt(String plainText) {
        return new BCryptPasswordEncoder(bcryptStrength).encode(plainText);
    }

    public boolean matchesBcrypt(String plainText, String hashValue) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(bcryptStrength);
        return passwordEncoder.matches(plainText, hashValue);
    }
}
