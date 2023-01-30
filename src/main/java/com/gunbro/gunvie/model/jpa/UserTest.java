package com.gunbro.gunvie.model.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UserTest() {
        this.createdAt = LocalDateTime.now();
    }

    public UserTest(String loginId, String password ) {
        this.loginId = loginId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
