package com.gunbro.gunvie.model.requestDto.User;

import com.gunbro.gunvie.config.enumData.EmailType;
import lombok.Data;

@Data
public class SearchPwRequestDto {
    private String name;
    private String loginId;
    private String email;
    private EmailType emailType;
}
