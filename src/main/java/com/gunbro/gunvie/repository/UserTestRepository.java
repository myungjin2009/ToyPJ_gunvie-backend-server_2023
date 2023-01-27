package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.model.jpa.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {

    List<UserTest> findByloginId(String loginId);
}
