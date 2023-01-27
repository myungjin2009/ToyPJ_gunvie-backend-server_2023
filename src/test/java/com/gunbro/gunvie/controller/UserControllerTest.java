package com.gunbro.gunvie.controller;

import com.gunbro.gunvie.model.jpa.UserTest;
import com.gunbro.gunvie.repository.UserTestRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Autowired
    UserTestRepository userTestRepository;

    @Test
    public void 데이터베이스테스트() {
        UserTest userTest = new UserTest();
        userTest.setLoginId("hello1");
        userTest.setPassword("1234");
        userTestRepository.save(userTest);
    }
}