package com.gunbro.gunvie.service;

import com.gunbro.gunvie.model.jpa.Follow;
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
}
