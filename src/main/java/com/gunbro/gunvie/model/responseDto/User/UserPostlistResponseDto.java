package com.gunbro.gunvie.model.responseDto.User;

import com.gunbro.gunvie.model.jpa.User;
import com.gunbro.gunvie.model.responseDto.DefaultDto;
import com.gunbro.gunvie.model.responseDto.Post.PostList;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserPostlistResponseDto extends DefaultDto {

    private User user;
    private List<PostList> postList;
}
