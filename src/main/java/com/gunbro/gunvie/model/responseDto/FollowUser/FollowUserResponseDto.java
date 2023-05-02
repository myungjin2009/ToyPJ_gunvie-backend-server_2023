package com.gunbro.gunvie.model.responseDto.FollowUser;
import com.gunbro.gunvie.model.responseDto.DefaultDto;

import java.util.List;

public class FollowUserResponseDto extends DefaultDto {

    List<FollowUserList> List;

    public java.util.List<FollowUserList> getList() {
        return List;
    }

    public void setList(java.util.List<FollowUserList> list) {
        List = list;
    }
}
