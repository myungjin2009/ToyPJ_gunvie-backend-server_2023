package com.gunbro.gunvie.model.jpa;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class FollowEmbed {

    @ManyToOne
    @JoinColumn
    private User following;

    @ManyToOne
    @JoinColumn
    private User follower;

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
