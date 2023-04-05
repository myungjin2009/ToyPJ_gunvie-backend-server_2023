package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.config.enumData.LoginType;
import com.gunbro.gunvie.model.jpa.Follow;
import com.gunbro.gunvie.model.jpa.FollowEmbed;
import com.gunbro.gunvie.model.jpa.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class FollowRepositoryTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    void createAndSaveUser(String name) {
        User user = new User();
        user.setLoginId(name);
        user.setName("NAME" + name);
        user.setBirth("19980101");
        user.setPassword(name + "1234");
        user.setEmail(name + "@naver.com");
        user.setLoginType(LoginType.LOCAL);
        userRepository.save(user);
    }

    @Test
    @Transactional
    void FollowTest1() {
        createAndSaveUser("aaa1");
        createAndSaveUser("aaa2");
        createAndSaveUser("aaa3");



        //aaa1이 aaa2를 follow 했음
        FollowEmbed followUser1ToUser2 = new FollowEmbed();
        followUser1ToUser2.setFollower(userRepository.findByLoginId("aaa1"));
        followUser1ToUser2.setFollowing(userRepository.findByLoginId("aaa2"));

        Follow follow1 = new Follow();
        follow1.setFollowId(followUser1ToUser2);
        followRepository.save(follow1);

        //aaa1이 aaa3도 follow 했음
        FollowEmbed followUser1ToUser3 = new FollowEmbed();
        followUser1ToUser3.setFollower(userRepository.findByLoginId("aaa1"));
        followUser1ToUser3.setFollowing(userRepository.findByLoginId("aaa3"));

        Follow follow2 = new Follow();
        follow2.setFollowId(followUser1ToUser3);
        followRepository.save(follow2);

        //user2이 user1을 follow 했음
        FollowEmbed followUser2ToUser1 = new FollowEmbed();
        followUser2ToUser1.setFollower(userRepository.findByLoginId("aaa2"));
        followUser2ToUser1.setFollowing(userRepository.findByLoginId("aaa1"));

        Follow follow3 = new Follow();
        follow3.setFollowId(followUser2ToUser1);
        followRepository.save(follow3);

        List<Follow> testResult = followRepository.findFollowingAll(userRepository.findByLoginId("aaa1"));

        Assertions.assertThat(testResult.get(0).getFollowId().getFollowing().getName())
                .isEqualTo("NAMEaaa2");
        Assertions.assertThat(testResult.get(1).getFollowId().getFollowing().getName())
                .isEqualTo("NAMEaaa3");
    }


}