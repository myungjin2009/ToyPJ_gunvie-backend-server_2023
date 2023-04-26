package com.gunbro.gunvie.service;

import com.gunbro.gunvie.model.jpa.Follow;
import com.gunbro.gunvie.model.jpa.FollowEmbed;
import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Page<Follow> showFollowingUsers(int page, User user) {
        Pageable pageable = PageRequest.of(page,12, Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<Follow> result = followRepository.findFollowing(user, pageable);
        if(result.isEmpty()) {
           return null;
        }

        return result;
    }

    public Page<Follow> showFollowerUsers(int page, User user) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Follow> result = followRepository.findFollower(user, pageable);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public int userFollow(User loginUser, User toFollowUser) {
        Follow findResult = followRepository.findFollowOne(loginUser, toFollowUser);
        if(findResult != null) {
            return 1;
        }

        FollowEmbed followEmbed = new FollowEmbed();
        followEmbed.setFollower(loginUser);
        followEmbed.setFollowing(toFollowUser);

        Follow follow = new Follow();
        follow.setFollowId(followEmbed);
        followRepository.save(follow);
        return 0;
    }

    public void deleteFollow(User loginUser, User toDeleteFollowUser) {
        FollowEmbed followEmbed = new FollowEmbed();
        followEmbed.setFollower(loginUser);
        followEmbed.setFollowing(toDeleteFollowUser);

        Follow follow = new Follow();
        follow.setFollowId(followEmbed);
        followRepository.delete(follow);
    }
}
