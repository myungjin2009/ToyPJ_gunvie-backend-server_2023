package com.gunbro.gunvie.model.responseDto.User;

import com.gunbro.gunvie.config.enumData.Gender;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import lombok.Data;

@Data
public class UserAuthResponseDto extends DefaultDto {
    private Gender gender;
    private String name;
    private String email;
}
