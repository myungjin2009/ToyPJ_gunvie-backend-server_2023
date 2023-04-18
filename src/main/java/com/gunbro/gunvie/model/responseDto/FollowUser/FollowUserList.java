package com.gunbro.gunvie.model.responseDto.FollowUser;

import com.gunbro.gunvie.config.enumData.FollowType;
import com.gunbro.gunvie.config.enumData.Gender;
import com.gunbro.gunvie.model.jpa.Follow;

public class FollowUserList {
    private Long id;

    private String name;

    private String snsId;

    private Gender gender;

    private String email;

    private String img;

    public FollowUserList(Long id, String name, String snsId, Gender gender, String email, String img) {
        this.id = id;
        this.name = name;
        this.snsId = snsId;
        this.gender = gender;
        this.email = email;
        this.img = img;
    }

    public FollowUserList(Follow follow, FollowType type) {
        if(type == FollowType.FOLLOWING) {
            this.id = follow.getFollowId().getFollowing().getId();
            this.name = follow.getFollowId().getFollowing().getName();
            this.snsId = follow.getFollowId().getFollowing().getSnsId();
            this.gender = follow.getFollowId().getFollowing().getGender();
            this.email = follow.getFollowId().getFollowing().getEmail();
            this.img = follow.getFollowId().getFollowing().getImg();
        } else if(type == FollowType.FOLLOWER) {
            this.id = follow.getFollowId().getFollower().getId();
            this.name = follow.getFollowId().getFollower().getName();
            this.snsId = follow.getFollowId().getFollower().getSnsId();
            this.gender = follow.getFollowId().getFollower().getGender();
            this.email = follow.getFollowId().getFollower().getEmail();
            this.img = follow.getFollowId().getFollower().getImg();
        }

    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSnsId() {
        return snsId;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getImg() {
        return img;
    }
}
