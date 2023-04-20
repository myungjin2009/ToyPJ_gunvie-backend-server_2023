package com.gunbro.gunvie.model.responseDto.Post;

import com.gunbro.gunvie.model.responseDto.DefaultDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostListResponseDto extends DefaultDto {

    private List<PostList> postLists;
}
