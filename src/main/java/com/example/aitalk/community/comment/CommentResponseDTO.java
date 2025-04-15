package com.example.aitalk.community.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDTO {
    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDTO(Long id, String nickname, String content, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
