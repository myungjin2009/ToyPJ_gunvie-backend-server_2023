package com.gunbro.gunvie.model.requestDto.User;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPwRequestDto {
    private String name;
    private String loginId;
    private String email;
    private String resetPw;
}

