package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.model.jpa.Follow;
import com.gunbro.gunvie.model.jpa.FollowEmbed;
import com.gunbro.gunvie.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowEmbed> {

    @Query("SELECT u FROM Follow u WHERE u.followId.follower = :followerId")
    List<Follow> findFollowing(@Param("followerId") User user);
}
