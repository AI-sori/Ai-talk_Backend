package com.example.aitalk.community;

import com.example.aitalk.community.comment.CommentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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
