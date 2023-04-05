package com.gunbro.gunvie.model.responseDto;

import com.gunbro.gunvie.config.enumData.Gender;

public class FollowUserResponseDto {

    private Long id;

    private String name;

    private String snsId;

    private Gender gender;

    private String email;

    private String img;

    public FollowUserResponseDto(Long id, String name, String snsId, Gender gender, String email, String img) {
        this.id = id;
        this.name = name;
        this.snsId = snsId;
        this.gender = gender;
        this.email = email;
        this.img = img;
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
