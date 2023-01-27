package com.gunbro.gunvie.model.jpa;

import com.gunbro.gunvie.config.enumData.Gender;
import com.gunbro.gunvie.config.enumData.LoginType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birth;

    @Column
    private String password;

    @Column
    private String snsId;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LoginType loginType;

    @Column
    private String img;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;



}
