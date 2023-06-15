package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.model.jpa.Follow;
import com.gunbro.gunvie.model.jpa.FollowEmbed;
import com.gunbro.gunvie.model.jpa.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowEmbed> {

    @Query("SELECT u FROM Follow u WHERE u.followId.follower = :followerId")
    List<Follow> findFollowingAll(@Param("followerId") User user);

    @Query("SELECT u FROM Follow u WHERE u.followId.follower = :followerId")
    Page<Follow> findFollowing(@Param("followerId") User user, Pageable pageable);

    @Query("SELECT u FROM Follow u WHERE u.followId.following = :followingId")
    Page<Follow> findFollower(@Param("followingId") User user, Pageable pageable);

    @Query("SELECT COUNT(u) FROM Follow u WHERE u.followId.follower = :followerId")
    Optional<Long> findFollowingCount(@Param("followerId") User user);

    @Query("SELECT COUNT(u) FROM Follow u WHERE u.followId.following = :followingId")
    Optional<Long> findFollowerCount(@Param("followingId") User user);

    @Query("SELECT u FROM Follow u WHERE u.followId.follower = :follower AND u.followId.following = :following")
    Follow findFollowOne(@Param("follower") User followerUser, @Param("following") User toFollowUser);
}
