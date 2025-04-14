package com.example.aitalk.community;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPostRequestDTO {
//    private Long userId;
    private String category;
    private String title;
    private String content;
    private String image;
}
