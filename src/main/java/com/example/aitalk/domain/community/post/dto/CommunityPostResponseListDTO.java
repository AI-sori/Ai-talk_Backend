package com.example.aitalk.domain.community.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityPostResponseListDTO {
    private Long postId;
    private String nickname;
    private String category;
    private String title;
    private String content;
    private String image;

    private int likeCount;

    private int commentCount;

}
