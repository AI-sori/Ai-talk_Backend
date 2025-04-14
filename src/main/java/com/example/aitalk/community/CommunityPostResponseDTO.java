package com.example.aitalk.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityPostResponseDTO {
    private Long postId;
    private String nickname;
    private String category;
    private String title;
    private String content;
    private String image;
}
