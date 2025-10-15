package com.example.aitalk.domain.community.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDTO {
    private Long id;
    private String nickname;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDTO(Long id, String nickname, Long userId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
