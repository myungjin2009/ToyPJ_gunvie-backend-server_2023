package com.gunbro.gunvie.model.responseDto.User;

import com.gunbro.gunvie.model.responseDto.DefaultDto;

public class SearchIdResponseDto extends DefaultDto {
    private String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
