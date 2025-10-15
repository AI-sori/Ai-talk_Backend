package com.example.aitalk.domain.community.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDTO {
    private Long postId;
    private String content;
}
